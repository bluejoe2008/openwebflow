package org.openwebflow.mvc;

public interface WebFlowConfiguration
{
	String getDefaultClaimTaskActionView();

	String getDefaultCompleteTaskActionView();

	String getDefaultCompleteTaskFormView();

	String getDefaultStartProcessActionView();

	String getDefaultStartProcessFormView();

	FormVariablesFilter getFormVariablesFilter();
}