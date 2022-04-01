/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
	
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.maccount.module;

import io.surati.gap.admin.base.api.Access;
import io.surati.gap.admin.base.api.Module;
import io.surati.gap.payment.base.module.PaymentBaseModule;

/**
 * Access right for a user.
 *
 * @since 0.3
 */
public enum ManagementAccountAccess implements Access {

	CONFIGURER_LIASSES("Configurer les liasses", ""),
	CONFIGURER_TITRES("Configurer les titres", ""),
	CONFIGURER_SECTIONS("Configurer les sections", ""),
	DEFINIR_DONNEES_COMPTE_GESTION_DOC_REF("Définir les données de compte de gestion d'un document de référence", ""),
	CONFIGURER_SEUILS_ENLIASSEMENT("Configurer les seuils d'enliassement", ""),
	VISUALISER_DOCUMENTS_A_ENLIASER("Visualiser les documents à enliasser", ""),
	ENLIASSER_DOCUMENTS("Enliasser les documents", "");

	static {
		for (Access acs : ManagementAccountAccess.values()) {
			Access.VALUES.add(acs);
			Access.BY_CODE.put(acs.code(), acs);
		}
	}

	/**
	 * Title of access.
	 */
	private String title;

	/**
	 * Description.
	 */
	private String description;

	/**
	 * Ctor.
	 * @param title Title
	 * @param description Description
	 */
	ManagementAccountAccess(final String title, final String description) {
		this.title = title;
		this.description = description;
	}
	
	/**
	 * Get title.
	 * @return Title
	 */
	@Override
	public String title() {
		return this.title;
	}

	/**
	 * Get Code.
	 * @return Code
	 */
	public String code() {
		return this.name();
	}

	/**
	 * Get description.
	 * @return Code
	 */
	public String description() {
		return this.description;
	}

	/**
	 * Get module.
	 * @return Module name
	 */
	public Module module() {
		return PaymentBaseModule.PAYMENT_BASE;
	}
	
	@Override
	public String toString() {
		return this.title;
	}
}
