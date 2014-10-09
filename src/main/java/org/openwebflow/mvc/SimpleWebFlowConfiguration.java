package org.openwebflow.mvc;

public class SimpleWebFlowConfiguration implements WebFlowConfiguration
{
	private String _defaultClaimTaskActionView;

	private String _defaultCompleteTaskActionView;

	private String _defaultCompleteTaskFormView;

	private String _defaultStartProcessActionView;

	private String _defaultStartProcessFormView;

	private FormVariablesFilter _formVariablesFilter;

	public String getDefaultClaimTaskActionView()
	{
		return _defaultClaimTaskActionView;
	}

	@Override
	public String getDefaultCompleteTaskActionView()
	{
		return _defaultCompleteTaskActionView;
	}

	@Override
	public String getDefaultCompleteTaskFormView()
	{
		return _defaultCompleteTaskFormView;
	}

	@Override
	public String getDefaultStartProcessActionView()
	{
		return _defaultStartProcessActionView;
	}

	@Override
	public String getDefaultStartProcessFormView()
	{
		return _defaultStartProcessFormView;
	}

	public FormVariablesFilter getFormVariablesFilter()
	{
		return _formVariablesFilter;
	}

	public void setDefaultClaimTaskActionView(String defaultClaimTaskActionView)
	{
		_defaultClaimTaskActionView = defaultClaimTaskActionView;
	}

	public void setDefaultCompleteTaskActionView(String defaultCompleteTaskActionView)
	{
		_defaultCompleteTaskActionView = defaultCompleteTaskActionView;
	}

	public void setDefaultCompleteTaskFormView(String defaultCompleteTaskFormView)
	{
		_defaultCompleteTaskFormView = defaultCompleteTaskFormView;
	}

	public void setDefaultStartProcessActionView(String defaultStartProcessActionView)
	{
		_defaultStartProcessActionView = defaultStartProcessActionView;
	}

	public void setDefaultStartProcessFormView(String defaultStartProcessFormView)
	{
		_defaultStartProcessFormView = defaultStartProcessFormView;
	}

	public void setFormVariablesFilter(FormVariablesFilter formVariablesFilter)
	{
		_formVariablesFilter = formVariablesFilter;
	}
}
