package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	Faker faker;
	User userpayload;
	
	@BeforeClass
	public void setupData() {
		faker= new Faker();
		userpayload= new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5, 10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
	}
	@Test(priority=1)
	public void testPostUser() {
		Response response = UserEndPoints.CreateUser(userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(priority=2)
	public void testGetUserByName() {
		Response response = UserEndPoints.ReadUser(this.userpayload.getUsername());
		response.then().log().all();
		int statuscode=response.getStatusCode();
		System.out.println("Status code is " +statuscode);
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response=UserEndPoints.UpdateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().all();
		
		Response responseAfterUpdate = UserEndPoints.ReadUser(this.userpayload.getUsername());
		responseAfterUpdate.then().log().all();
	}
	@Test(priority=4)
	public void testDeleteUser() {
		Response response = UserEndPoints.DeleteUser(this.userpayload.getUsername());
		response.then().log().all();
		int statuscode=response.getStatusCode();
		System.out.println("Status code after deletion is "+statuscode);
	}

}
