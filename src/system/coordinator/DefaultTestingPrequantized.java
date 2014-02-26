package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.testgenerators.Test;
import system.coordinator.testgenerators.TestGenerator;
import system.hasher.Hasher;

public class DefaultTestingPrequantized extends DefaultTesting{

	{}// TODO Jen/Jim - SQL prequantizing
	
	public DefaultTestingPrequantized(Hasher hasher, Users users,
			TestGenerator testGenerator) {
		super(hasher, users, testGenerator);
		prequantize();
	}
	
	private void prequantize(){
		Long total = 0L;
		Long completed = 0L;
		Double progress;
		for(User user : users.users){
			total += user.readings.size();
		}
		
		for(User user : users.users){
			int numberOfReadings = user.readings.size();
			for(int i=0; i<numberOfReadings; i++){
				user.prequantizedEnrolledTemplates.add(hasher.makeEnrollTemplate(user.readings.get(i)));
				user.prequantizedTestTemplates.add(hasher.makeTestTemplates(user.readings.get(i)));
				completed++;
				progress = (completed.doubleValue()/total.doubleValue())*100.0;
				System.out.format("prequantizing: %5.2f%%%n", progress);
			}
			progress = (completed.doubleValue()/total.doubleValue())*100.0;
//			System.out.format("prequantizing: %5.2f%%%n", progress);
		}
	}

	@Override
	protected Double runTest(Test test){ //FIXME null pointer for allTests, but not for FVC tests.
		Template enrolledTemplate = users.users.get(test.enrolledUserID.intValue()).prequantizedEnrolledTemplates.get(test.enrolledReadingNumber.intValue());
		ArrayList<Template> testTemplates = users.users.get(test.testUserID.intValue()).prequantizedTestTemplates.get(test.testReadingNumber.intValue());
		
		Double score = hasher.compareTemplates(enrolledTemplate, testTemplates);
		
		return score; 
	}
	
}
