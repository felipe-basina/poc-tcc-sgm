package poc.tcc.sgm.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserValidationService {

	private static final int CPF_TOTAL_CHARACTERS = 11;
	
	public boolean isValidCpf(final String document) {
		log.info("Validating document {}", document);
		if (Objects.isNull(document)) {
			return Boolean.FALSE;
		}
		final String newDocument = document.replace(".", "")
										.replace("-", "")
										.replace(" ", "")
									.trim();
		return newDocument.length() == CPF_TOTAL_CHARACTERS ? Boolean.TRUE : Boolean.FALSE;
	}
	
	public boolean isServidorUser(final String document) {
		if (!this.isValidCpf(document)) {
			return Boolean.FALSE;
		}
		final String lastDigitAsString = document.substring(document.length() - 1);
		final int lastDigit = Integer.parseInt(lastDigitAsString);
		final boolean isServidorUser = lastDigit % 2 > 0;
		log.info("Is {} a servidor user? {}", document, isServidorUser);
		return isServidorUser;
	}
	
}
