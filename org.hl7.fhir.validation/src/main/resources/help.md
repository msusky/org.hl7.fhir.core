% VALIDATOR\_CLI(1) 5.1.18+b7661d | HL7 FHIR Java CLI Validator
% Grahame Grieve
  James Agnew
  FHIR community
% $date$

# NAME
validator\_cli.jar - validate or transform HL7 FHIR resources or bundles

# SYNOPSIS
java -jar validator\_cli.jar [SOURCE]... [OPTION]...

# DESCRIPTION
The validator compares a resource against the base definitions and any profiles
declared in the resource (Resource.meta.profile) or specified on the command
line.

Schema and schematron checking is performed, depending on the FHIR version
as followed:

- XML and JSON Schema: FHIR versions 1.0, 1.4, 3.0, 4.0, 4.5
- Turtle: FHIR versions 3.0, 4.0, 4.5

# OPTIONS

**SOURCE**
: A file, URL, directory or pattern for resources to validate. At least one
  source must be declared. If there is more than one source or if the source 
  is other than a single file or URL and the output parameter is used, results
  will be provided as a *Bundle*. Patterns are limited to a directory 
  followed by a filename with an embedded asterisk.

**-assumeValidRestReferences**
: If present, assume that URLs that reference resources follow the RESTful
  URI pattern and it is safe to infer the type from the URL.
  Default: *disabled*

**-convert**
: Convert a resource or logical model. This parameter requires the parameters
  **SOURCE** and **-output**. The parameter **-ig** may be used to provide
  a logical model.
  Default: *disabled*

**-debug**
: Produce additional information about the loading and validation process.
  Default: *disabled*

**-fhirpath [expression]**
: Evaluate a FHIRPath expression on a resource or logical model. This requires
  the parameter **SOURCE**. The parameter **-ig** may be used to provide a
  logical model.

**-hintAboutNonMustSupport**
: If present, raise hints if the instance contains data elements that are not
  marked as *mustSupport=true*. Useful to identify elements included that may
  be ignored by recipients.
  Default: *disabled*

**-ig [package|file|folder|url]**
: An implementation guide or profile definition to load. Can be 
  the URL of an implementation guide or a package ([id]-[ver]) for
  a built implementation guide or a local folder that contains a
  set of conformance resources. This parameter can appear any number of times.
  Default: *none*

**-language: [lang]**
: Specifies the language to use when validating coding displays (same value as
  for xml:lang). This option is ignored if the resource specifies a language.
  Default: *none*

**-locale \<code>**
: Specifies the locale or language of the validation result messages, e.g. de
  to display validation result messages in german language. *Note: Locales can
  only be used if translations have been provided.*
  Default: *en*

**-narrative**
: Generate narrative for a resource. This parameter requires the parameters
  **-defn**, **-tx**, **SOURCE**, and **-output**. The parameters
  **-ig** and **-profile** may be used.
  Default: *disabled*

**-native [true|false]**
: Use XML schema (W3C XML Schema and schematron), JSON schema and RDP (ShEx)
  for validation as well.
  Default: *false*

**-output \<file>**
: Write the validation result messages to a XML formatted output file (instead
  to *stdout*) as *OperationOutcome* resource or *Bundle* of multiple
  *OperationOutcome* resources, one for each validated FHIR resource. If no
  output file has been given, an error occurs.
  Default: -

**-profile [url]**
: The canonical URL to validate against (same as if it was specified in 
  Resource.meta.profile). If no profile is specified, the resource is 
  validated against the base specification. This parameter can appear any 
  number of times. (Note: The profile and its dependencies have to be made 
  available through one of the **-ig** parameters. Package dependencies will 
  automatically be resolved.)
  Default: *none*

**-proxy [address]:[port]**
: Specifies the proxy address and port to use.
  Default: *disabled*

**-questionnaire <NONE|CHECK|REQUIRED>**
: Validate any *QuestionnaireResponse* resource against its matching
  *Questionnaire* resource referenced by the canonical URL in 
  *QuestionnaireResponse.questionnaire*. Possible arguments are: *NONE* 
  (do not validate), *CHECK* (validate iff matching questionnaire is provided)
  and *REQUIRED* (validate and report an error if no matching questionnaire is
  provided). *Note: Arguments for this parameter must be provided case
  sensitive.* Default: *NONE*

**-recurse**
: Look in subfolders when **-ig** refers to a folder.
  Default: *disabled*

**-sct [intl|us|uk|au|nl|ca|se|dk|es]**
: Specify the edition of SNOMED CT to use. The default FHIR terminology
  service only supports a subset. To add to this list or the default FHIR
  terminology service ask on https://chat.fhir.org/#narrow/stream/179202-terminology.
  Default: *disabled*

**-security-checks**
: If present, check that string content doesn't include any HTML-like tags that
  might create problems downstream (though all external input must always be
  sanitized by escaping for either HTML or SQL).
  Default: *disabled*

**-snapshot**
: Generate a snapshot for a profile. This requires the parameters **-defn**,
  **-tx**, **SOURCE**, and **-output**. The parameter **-ig** may be
  used to provide necessary base profiles.
  Default: *disabled*

**-strictExtensions**
: If present, treat extensions not defined within the specified FHIR version
  and any referenced implementation guides or profiles as *error*.
  Default: *information*

**-transform [uri]**
: Execute a transformation as described by a structure map given by its
  URI the transformation starts with. Any other dependency maps have to be
  loaded using **-ig** parameters to reference those maps. (Note: This
  parameter uses the parameters **-defn**, **-tx**, **-ig** and **-output**.
  Default: *disabled*

**-tx [url]**
: The base URL of a FHIR terminology service. To run without terminology
  value, specify *n/a* as URL. This parameter can appear only once.
  Default: *http://tx.fhir.org*

**-txLog [file]**
: Produce a log of the terminology server operations in [file].
  Default: *disabled*

**-version <1.0|1.0.2|1.4|1.4.0|3.0|3.0.2|4.0.1|4.5|4.5.0>**
: Validate resources against the passed version of the base FHIR specification.
  It is recommended to set the version parameter at first. Before validation 
  starts a check for required packages matching the version is performed.
  Missing packages will be downloaded and installed to the FHIR package cache.
  *Note: Alternatively the version could be set by using the **-defn** or 
  **-ig** parameter to maintain backwards compatibility.*
  Default: *current build version* (3 or 5 letter version string)

# EXAMPLES

*Note: The validator CLI in general is executed in the Java Runtime Environment
and packaged as executable jar file. To improve readability the **java -jar**
command (prefix) always has been omitted for the examples presented.*

## OUTPUT FORMATS

**validator\_cli.jar ./patient.xml -output ./results.xml**
: Write validation result messages to the a file.

Validate patient resource and write result messages to the a file:
```
java -jar validator_cli.jar ./patient.xml -output ./results.xml
```

# BUGS

https://github.com/hapifhir/org.hl7.fhir.core/issues

# COPYRIGHT

https://github.com/hapifhir/org.hl7.fhir.core/blob/master/LICENSE.txt