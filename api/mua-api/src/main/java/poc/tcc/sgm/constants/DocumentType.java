package poc.tcc.sgm.constants;

import java.util.stream.Stream;

public enum DocumentType {

	CPF, CNPJ;
	
	public static DocumentType getDocumentType(final String documentTypeAsString) {
		return Stream.of(DocumentType.values())
				.filter(docType -> docType.name().equalsIgnoreCase(documentTypeAsString))
				.findFirst()
			.get();
	}
	
}
