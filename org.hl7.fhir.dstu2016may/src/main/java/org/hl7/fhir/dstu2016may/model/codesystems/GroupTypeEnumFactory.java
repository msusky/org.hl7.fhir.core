package org.hl7.fhir.dstu2016may.model.codesystems;

/*-
 * #%L
 * org.hl7.fhir.dstu2016may
 * %%
 * Copyright (C) 2014 - 2019 Health Level 7
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


/*
  Copyright (c) 2011+, HL7, Inc.
  All rights reserved.
  
  Redistribution and use in source and binary forms, with or without modification, 
  are permitted provided that the following conditions are met:
  
   * Redistributions of source code must retain the above copyright notice, this 
     list of conditions and the following disclaimer.
   * Redistributions in binary form must reproduce the above copyright notice, 
     this list of conditions and the following disclaimer in the documentation 
     and/or other materials provided with the distribution.
   * Neither the name of HL7 nor the names of its contributors may be used to 
     endorse or promote products derived from this software without specific 
     prior written permission.
  
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
  POSSIBILITY OF SUCH DAMAGE.
  
*/

// Generated on Sun, May 8, 2016 03:05+1000 for FHIR v1.4.0


import org.hl7.fhir.dstu2016may.model.EnumFactory;

public class GroupTypeEnumFactory implements EnumFactory<GroupType> {

  public GroupType fromCode(String codeString) throws IllegalArgumentException {
    if (codeString == null || "".equals(codeString))
      return null;
    if ("person".equals(codeString))
      return GroupType.PERSON;
    if ("animal".equals(codeString))
      return GroupType.ANIMAL;
    if ("practitioner".equals(codeString))
      return GroupType.PRACTITIONER;
    if ("device".equals(codeString))
      return GroupType.DEVICE;
    if ("medication".equals(codeString))
      return GroupType.MEDICATION;
    if ("substance".equals(codeString))
      return GroupType.SUBSTANCE;
    throw new IllegalArgumentException("Unknown GroupType code '"+codeString+"'");
  }

  public String toCode(GroupType code) {
    if (code == GroupType.PERSON)
      return "person";
    if (code == GroupType.ANIMAL)
      return "animal";
    if (code == GroupType.PRACTITIONER)
      return "practitioner";
    if (code == GroupType.DEVICE)
      return "device";
    if (code == GroupType.MEDICATION)
      return "medication";
    if (code == GroupType.SUBSTANCE)
      return "substance";
    return "?";
  }

    public String toSystem(GroupType code) {
      return code.getSystem();
      }

}
