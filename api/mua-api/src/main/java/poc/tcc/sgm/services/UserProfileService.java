package poc.tcc.sgm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import poc.tcc.sgm.dtos.CheckUserResponse;
import poc.tcc.sgm.dtos.UserInDTO;
import poc.tcc.sgm.gateways.SafimAPIClient;
import poc.tcc.sgm.models.UserProfile;
import poc.tcc.sgm.repositories.UserProfileRepository;

@Slf4j
@Service
public class UserProfileService {
	
	private UserProfileRepository userProfileRepository;
	
	private SafimAPIClient safimAPIClient;
	
	public static final Long MUNICIPE_ID = 1L;
	public static final Long SERVIDOR_ID = 2L;
	
	@Autowired
	public UserProfileService(UserProfileRepository userProfileRepository, SafimAPIClient safimAPIClient) {
		this.userProfileRepository = userProfileRepository;
		this.safimAPIClient = safimAPIClient;
	}
	
	public UserProfile getUserProfile(UserInDTO userInDTO) {
		if (!userInDTO.isCPFdocumentType()) {
			log.info("User {} with document type {}!", userInDTO.getUserName(), userInDTO.getDocumentType());
			return this.userProfileRepository.findById(MUNICIPE_ID).get();	
		}
		CheckUserResponse checkUserResponse = this.safimAPIClient.checkServerUser(userInDTO.getDocumentNumber());
		if (checkUserResponse.getServerUser()) {
			log.info("User {} has servidor profile!", userInDTO.getUserName());
			return this.userProfileRepository.findById(SERVIDOR_ID).get();
		}
		return this.userProfileRepository.findById(MUNICIPE_ID).get();
	}

}
