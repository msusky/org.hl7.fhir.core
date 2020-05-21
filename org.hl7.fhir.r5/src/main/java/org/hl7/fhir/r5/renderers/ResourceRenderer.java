package org.hl7.fhir.r5.renderers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.NotImplementedException;
import org.hl7.fhir.exceptions.DefinitionException;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.exceptions.FHIRFormatError;
import org.hl7.fhir.r5.model.Base;
import org.hl7.fhir.r5.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r5.model.CodeSystem.ConceptDefinitionComponent;
import org.hl7.fhir.r5.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r5.model.CanonicalResource;
import org.hl7.fhir.r5.model.CodeSystem;
import org.hl7.fhir.r5.model.DomainResource;
import org.hl7.fhir.r5.model.Narrative;
import org.hl7.fhir.r5.model.Narrative.NarrativeStatus;
import org.hl7.fhir.r5.model.Reference;
import org.hl7.fhir.r5.model.Resource;
import org.hl7.fhir.r5.renderers.utils.BaseWrappers.BaseWrapper;
import org.hl7.fhir.r5.renderers.utils.BaseWrappers.PropertyWrapper;
import org.hl7.fhir.r5.renderers.utils.BaseWrappers.ResourceWrapper;
import org.hl7.fhir.r5.renderers.utils.DirectWrappers.ResourceWrapperDirect;
import org.hl7.fhir.r5.renderers.utils.ElementWrappers.ResourceWrapperMetaElement;
import org.hl7.fhir.r5.renderers.utils.RenderingContext;
import org.hl7.fhir.r5.renderers.utils.Resolver.ResourceContext;
import org.hl7.fhir.r5.renderers.utils.Resolver.ResourceWithReference;
import org.hl7.fhir.r5.terminologies.CodeSystemUtilities;
import org.hl7.fhir.r5.utils.EOperationOutcome;
import org.hl7.fhir.r5.utils.ToolingExtensions;
import org.hl7.fhir.r5.utils.XVerExtensionManager;
import org.hl7.fhir.utilities.xhtml.NodeType;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;

public abstract class ResourceRenderer extends DataRenderer {

  protected ResourceContext rcontext;
  protected XVerExtensionManager xverManager;
  
  
  public ResourceRenderer(RenderingContext context) {
    super(context);
  }

  public ResourceRenderer(RenderingContext context, ResourceContext rcontext) {
    super(context);
    this.rcontext = rcontext;
  }

  public XhtmlNode build(DomainResource dr) throws FHIRFormatError, DefinitionException, FHIRException, IOException, EOperationOutcome {
    XhtmlNode x = new XhtmlNode(NodeType.Element, "div");
    render(x, dr);
    return x;
    
  }
  /**
   * given a resource, update it's narrative with the best rendering available
   * 
   * @param r - the domain resource in question
   * 
   * @throws IOException
   * @throws EOperationOutcome 
   * @throws FHIRException 
   */
  
  public void render(DomainResource r) throws IOException, FHIRException, EOperationOutcome {  
    XhtmlNode x = new XhtmlNode(NodeType.Element, "div");
    boolean hasExtensions = render(x, r);
    inject(r, x, hasExtensions ? NarrativeStatus.EXTENSIONS :  NarrativeStatus.GENERATED);
  }

  public XhtmlNode render(ResourceWrapper r) throws IOException, FHIRException, EOperationOutcome { 
    assert r.getContext() == context;
    XhtmlNode x = new XhtmlNode(NodeType.Element, "div");
    boolean hasExtensions = render(x, r);
    r.injectNarrative(x, hasExtensions ? NarrativeStatus.EXTENSIONS :  NarrativeStatus.GENERATED);
    return x;
  }

  public abstract boolean render(XhtmlNode x, DomainResource r) throws FHIRFormatError, DefinitionException, IOException, FHIRException, EOperationOutcome;
  
  public boolean render(XhtmlNode x, ResourceWrapper r) throws FHIRFormatError, DefinitionException, IOException, FHIRException, EOperationOutcome {
    throw new NotImplementedException("This is not implemented yet for resources of type "+r.getName());
  }
  
  public void describe(XhtmlNode x, DomainResource r) throws UnsupportedEncodingException, IOException {
    x.tx(display(r));
  }

  public abstract String display(DomainResource r) throws UnsupportedEncodingException, IOException;
  
  public static void inject(DomainResource r, XhtmlNode x, NarrativeStatus status) {
    if (!x.hasAttribute("xmlns"))
      x.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
    if (r.hasLanguage()) {
      // use both - see https://www.w3.org/TR/i18n-html-tech-lang/#langvalues
      x.setAttribute("lang", r.getLanguage());
      x.setAttribute("xml:lang", r.getLanguage());
    }
    if (!r.hasText() || !r.getText().hasDiv() || r.getText().getDiv().getChildNodes().isEmpty()) {
      r.setText(new Narrative());
      r.getText().setDiv(x);
      r.getText().setStatus(status);
    } else {
      XhtmlNode n = r.getText().getDiv();
      n.hr();
      n.getChildNodes().addAll(x.getChildNodes());
    }
  }

  public void renderReference(Resource res, XhtmlNode x, Reference r) throws UnsupportedEncodingException, IOException {
    ResourceWrapper rw = new ResourceWrapperDirect(this.context, res);
    renderReference(rw, x, r);
  }

  public void renderReference(ResourceWrapper rw, XhtmlNode x, Reference r) throws UnsupportedEncodingException, IOException {
    XhtmlNode c = x;
    ResourceWithReference tr = null;
    if (r.hasReferenceElement()) {
      tr = resolveReference(rw, r.getReference());

      if (!r.getReference().startsWith("#")) {
        if (tr != null && tr.getReference() != null)
          c = x.ah(tr.getReference());
        else
          c = x.ah(r.getReference());
      }
    }
    // what to display: if text is provided, then that. if the reference was resolved, then show the generated narrative
    if (r.hasDisplayElement()) {
      c.addText(r.getDisplay());
      if (tr != null && tr.getResource() != null) {
        c.tx(". Generated Summary: ");
        new ProfileDrivenRenderer(context).generateResourceSummary(c, tr.getResource(), true, r.getReference().startsWith("#"));
      }
    } else if (tr != null && tr.getResource() != null) {
      new ProfileDrivenRenderer(context).generateResourceSummary(c, tr.getResource(), r.getReference().startsWith("#"), r.getReference().startsWith("#"));
    } else {
      c.addText(r.getReference());
    }
  }

  public void renderReference(ResourceWrapper rw, XhtmlNode x, BaseWrapper r) throws UnsupportedEncodingException, IOException {
    XhtmlNode c = x;
    ResourceWithReference tr = null;
    String v;
    if (r.has("reference")) {
      v = r.get("reference").primitiveValue();
      tr = resolveReference(rw, v);

      if (!v.startsWith("#")) {
        if (tr != null && tr.getReference() != null)
          c = x.ah(tr.getReference());
        else
          c = x.ah(v);
      }
    } else {
      v = "";
    }
    // what to display: if text is provided, then that. if the reference was resolved, then show the generated narrative
    if (r.has("display")) {
      c.addText(r.get("display").primitiveValue());
      if (tr != null && tr.getResource() != null) {
        c.tx(". Generated Summary: ");
        new ProfileDrivenRenderer(context).generateResourceSummary(c, tr.getResource(), true, v.startsWith("#"));
      }
    } else if (tr != null && tr.getResource() != null) {
      new ProfileDrivenRenderer(context).generateResourceSummary(c, tr.getResource(), v.startsWith("#"), v.startsWith("#"));
    } else {
      c.addText(v);
    }
  }
  
  protected ResourceWithReference resolveReference(ResourceWrapper res, String url) {
    if (url == null)
      return null;
    if (url.startsWith("#")) {
      for (ResourceWrapper r : res.getContained()) {
        if (r.getId().equals(url.substring(1)))
          return new ResourceWithReference(null, r);
      }
      return null;
    }

    if (rcontext != null) {
      BundleEntryComponent bundleResource = rcontext.resolve(url);
      if (bundleResource != null) {
        String bundleUrl = "#" + bundleResource.getResource().getResourceType().name().toLowerCase() + "_" + bundleResource.getResource().getId(); 
        return new ResourceWithReference(bundleUrl, new ResourceWrapperDirect(this.context, bundleResource.getResource()));
      }
      org.hl7.fhir.r5.elementmodel.Element bundleElement = rcontext.resolveElement(url);
      if (bundleElement != null) {
        String bundleUrl = null;
        if (bundleElement.getNamedChild("resource").getChildValue("id") != null) {
          bundleUrl = "#" + bundleElement.fhirType().toLowerCase() + "_" + bundleElement.getNamedChild("resource").getChildValue("id");
        } else {
          bundleUrl = "#" +fullUrlToAnchor(bundleElement.getChildValue("fullUrl"));          
        }
        return new ResourceWithReference(bundleUrl, new ResourceWrapperMetaElement(this.context, bundleElement));
      }
    }

    Resource ae = getContext().getWorker().fetchResource(null, url);
    if (ae != null)
      return new ResourceWithReference(url, new ResourceWrapperDirect(this.context, ae));
    else if (context.getResolver() != null) {
      return context.getResolver().resolve(context, url);
    } else
      return null;
  }
  
  
  private String fullUrlToAnchor(String url) {
    return url.replace(":", "").replace("/", "_");
  }

  protected void generateCopyright(XhtmlNode x, CanonicalResource cs) {
    XhtmlNode p = x.para();
    p.b().tx(getContext().getWorker().translator().translate("xhtml-gen-cs", "Copyright Statement:", context.getLang()));
    smartAddText(p, " " + cs.getCopyright());
  }

  public String displayReference(Resource res, Reference r) throws UnsupportedEncodingException, IOException {
    return "todo"; 
   }
   

   public Base parseType(String string, String type) {
     return null;
   }

   protected PropertyWrapper getProperty(ResourceWrapper res, String name) {
     for (PropertyWrapper t : res.children()) {
       if (t.getName().equals(name))
         return t;
     }
     return null;
   }

   protected boolean valued(PropertyWrapper pw) {
     return pw != null && pw.hasValues();
   }


   protected ResourceWrapper fetchResource(BaseWrapper subject) throws UnsupportedEncodingException, FHIRException, IOException {
     if (context.getResolver() == null)
       return null;
     
     String url = subject.getChildByName("reference").value().getBase().primitiveValue();
     ResourceWithReference rr = context.getResolver().resolve(context, url);
     return rr == null ? null : rr.getResource();
   }


   protected String describeStatus(PublicationStatus status, boolean experimental) {
     switch (status) {
     case ACTIVE: return experimental ? "Experimental" : "Active"; 
     case DRAFT: return "draft";
     case RETIRED: return "retired";
     default: return "Unknown";
     }
   }

   protected void renderCommitteeLink(XhtmlNode x, CanonicalResource cr) {
     String code = ToolingExtensions.readStringExtension(cr, ToolingExtensions.EXT_WORKGROUP);
     CodeSystem cs = context.getWorker().fetchCodeSystem("http://terminology.hl7.org/CodeSystem/hl7-work-group");
     if (cs == null || !cs.hasUserData("path"))
       x.tx(code);
     else {
       ConceptDefinitionComponent cd = CodeSystemUtilities.findCode(cs.getConcept(), code);
       if (cd == null) {
         x.tx(code);
       } else {
         x.ah(cs.getUserString("path")+"#"+cs.getId()+"-"+cd.getCode()).tx(cd.getDisplay());
       }
     }
   }


}