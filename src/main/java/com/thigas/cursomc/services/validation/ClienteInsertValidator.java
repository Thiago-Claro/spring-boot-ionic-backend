package com.thigas.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.thigas.cursomc.domain.enums.TipoCliente;
import com.thigas.cursomc.dto.ClienteNewDto;
import com.thigas.cursomc.resources.exceptions.FieldMessage;
import com.thigas.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto> {

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDto objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj", "CPF inválido"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj", "CNPJ inválido"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();

	}

}
