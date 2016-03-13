/**
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the
 * specific language governing rights and limitations under the License.
 *
 * The Original Code is "CreateAMessage.java".  Description:
 * "Example Code"
 *
 * The Initial Developer of the Original Code is University Health Network. Copyright (C)
 * 2001.  All Rights Reserved.
 *
 * Contributor(s): James Agnew
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * GNU General Public License (the  �GPL�), in which case the provisions of the GPL are
 * applicable instead of those above.  If you wish to allow use of your version of this
 * file only under the terms of the GPL and not to allow others to use your version
 * of this file under the MPL, indicate your decision by deleting  the provisions above
 * and replace  them with the notice and other provisions required by the GPL License.
 * If you do not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the GPL.
 *
 */
package ca.uhn.hl7v2.examples;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v23.message.ADT_A01;
import ca.uhn.hl7v2.model.v23.segment.EVN;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.NK1;
import ca.uhn.hl7v2.model.v23.segment.PID;
import ca.uhn.hl7v2.model.v23.segment.PV1;
import ca.uhn.hl7v2.parser.Parser;
import java.util.Date;

/**
 * 
 * @author aervits
 * ADT_A01 Message Explanation
 * https://catalyze.io/learn/hl7-201-the-admission-discharge-transfer-adt-message
 * 
 * HAPI sources https://svn.code.sf.net/p/hl7api/code/trunk/hapi-mvn/hapi-examples/src/main/java/ca/uhn/hl7v2/examples/
 */

public class CreateAMessageADT_A01
{

    /**
     * @param args
     * @throws HL7Exception 
     */
    public static void main(String[] args) throws Exception {
        
        ADT_A01 adt = new ADT_A01();
        adt.initQuickstart("ADT", "A01", "P");
        
        // Populate the MSH Segment
        MSH mshSegment = adt.getMSH();
        mshSegment.getSendingApplication().getNamespaceID().setValue("TestSendingSystem");
        mshSegment.getSequenceNumber().setValue("123");
        
        EVN evnSegment = adt.getEVN();
        evnSegment.getEventTypeCode().setValue("A01");
        
        // Populate the PID Segment
        PID pid = adt.getPID(); 
        //pid.getPatientName(0).getFamilyName().getSurname().setValue("Doe");
        pid.getPatientName(0).getFamilyName().setValue("Doe");
        pid.getPatientName(0).getGivenName().setValue("John");
        //pid.getPatientIdentifierList(0).getID().setValue("123456");
        pid.getPatientIDExternalID().getID().setValue("123456");

        
        //Every field should be double-checked
        NK1 nk1Segment = adt.getNK1();
        nk1Segment.getContactPersonSName(0).getGivenName().setValue("APPLESEED");
        nk1Segment.getContactPersonSName(0).getFamilyName().setValue("BARBARA");
        
        PV1 pv1Segment = adt.getPV1();
        pv1Segment.getAdmissionType().setValue("SUR");
        pv1Segment.getAdmitDateTime().getTimeOfAnEvent().setValueToSecond(new Date());
        
        // Now, let's encode the message and look at the output
        HapiContext context = new DefaultHapiContext();
        Parser parser = context.getPipeParser();
        String encodedMessage = parser.encode(adt);
        System.out.println("Printing ER7 Encoded Message:");
        System.out.println(encodedMessage);
        
        /*
         * Prints:
         * 
         * MSH|^~\&|TestSendingSystem||||200701011539||ADT^A01^ADT A01||||123
         * PID|||123456||Doe^John
         */
    }

}