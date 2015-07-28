package org.sitenv.directvendortools.web.util;


import org.sitenv.directvendortools.web.util.ApplicationConstants;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(ApplicationConstants.RESPONSE)
public class ResponseTO
{

	private int returnCode;

	private ResultSetTO resultSet;
	
	private int insertQueryStatus;

	private GenericErrorTO error;
	
	private boolean booleanOutput = true;
	
	private String userAuthenticated;
	
	public void setError(final GenericErrorTO error)
	{
		this.error = error;
	}
	
	public ResponseTO (int insertQueryStatus)
	{
		this.insertQueryStatus = insertQueryStatus;
	}
	
	public ResponseTO (String userAuthenticated)
	{
		this.userAuthenticated = userAuthenticated;
	}
	
	
	public String getUserAuthenticated() {
		return userAuthenticated;
	}

	public void setUserAuthenticated(String userAuthenticated) {
		this.userAuthenticated = userAuthenticated;
	}

	public ResponseTO (boolean booleanOutput)
	{
		this.booleanOutput = booleanOutput;
	}

	public ResponseTO()
	{
	}
	
	public ResponseTO(final GenericErrorTO error)
	{
		this.returnCode = -1;
		this.error = error;
	}

	public ResponseTO(final ResultSetTO resultSet)
	{
		this.resultSet = resultSet;
		if (this.resultSet != null)
		{
			this.resultSet.initializeCounts();
		}
	}

	/**
	 * @return the returnCode
	 */
	public int getReturnCode()
	{
		return returnCode;
	}

	/**
	 * @param returnCode
	 *            the returnCode to set
	 */
	public void setReturnCode(final int returnCode)
	{
		this.returnCode = returnCode;
	}

	/**
	 * @return the resultSet
	 */
	public ResultSetTO getResultSet()
	{
		return resultSet;
	}

	/**
	 * @param resultSet
	 *            the resultSet to set
	 */
	public void setResultSet(final ResultSetTO resultSet)
	{
		this.resultSet = resultSet;
	}

	/**
	 * @return the error
	 */
	public GenericErrorTO getError()
	{
		return error;
	}

	public int getInsertQueryStatus() {
		return insertQueryStatus;
	}

	public void setInsertQueryStatus(int insertQueryStatus) {
		this.insertQueryStatus = insertQueryStatus;
	}

	public boolean isBooleanOutput() {
		return booleanOutput;
	}

	public void setBooleanOutput(boolean booleanOutput) {
		this.booleanOutput = booleanOutput;
	}

}
