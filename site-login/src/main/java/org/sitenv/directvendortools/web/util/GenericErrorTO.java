package org.sitenv.directvendortools.web.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.sitenv.directvendortools.web.util.ApplicationConstants;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias(ApplicationConstants.ERROR)
public class GenericErrorTO
{
	public static final String DEFAULT_ERROR_XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"
			+ "<error><errorCode>-999</ErrorCode>"
			+ "<errorMessage>Error during XML ERROR generation</errorMessage></error>";

	private String errorMessage;

	private int errorCode;

	private String errorStackTrace;


	/**
	 * Constructor.
	 */
	public GenericErrorTO()
	{
		this("Error");
	}

	/**
	 * Constructor.
	 * 
	 * @param errorMessage
	 *            Description of the error.
	 */
	public GenericErrorTO(final String errorMessage)
	{
		this.errorMessage = errorMessage;
		this.errorCode = -1;
	}

	/**
	 * Constructor.
	 */
	public GenericErrorTO(final String errorMessage, final int errorCode)
	{
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	/**
	 * Constructor.
	 */
	public GenericErrorTO(final String errorMessage, final int errorCode,
			final String errorStackTrace)
	{
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.errorStackTrace = errorStackTrace;
	}

	/**
	 * Constructor.
	 */
	public GenericErrorTO(final Exception exception)
	{
		this.errorStackTrace = getExceptionTrace(exception);
		this.errorMessage = getNestedExceptionMessage(exception);
		this.errorCode = -1;
	}


	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(final String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(final int errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorStackTrace
	 */
	public String getErrorStackTrace()
	{
		return errorStackTrace;
	}

	/**
	 * @param errorStackTrace
	 *            the errorStackTrace to set
	 */
	public void setErrorStackTrace(final String errorStackTrace)
	{
		this.errorStackTrace = errorStackTrace;
	}

	/**
	 * Checks the nested exception chain to see if this exception has a
	 * QueryException in the chain. If one is present, it is returned.
	 * Otherwise, a null value is returned.
	 * <p>
	 * This allows the better error reporting to a UI for database errors. By
	 * default, the exception message of the caught exception will be based on
	 * the last thrown exception, and not the root exception.
	 * 
	 * @param t
	 * @return null, or a QueryException if found
	 */
	private String getNestedExceptionMessage(final Throwable exception)
	{
		String returnValue;
		if (exception == null)
		{
			returnValue = DEFAULT_ERROR_XML;
		}
		else
		{
			final StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(exception.toString());
			Throwable cause = exception.getCause();
			while (cause != null)
			{
				stringBuilder.append(",");
				stringBuilder.append(cause.getMessage());
				cause = cause.getCause();
			}
			returnValue = stringBuilder.toString();
		}
		return returnValue;
	}

	private String getExceptionTrace(final Throwable exception)
	{
		String returnValue;
		if (exception == null)
		{
			returnValue = DEFAULT_ERROR_XML;
		}
		else
		{
			final StringWriter stringWriter = new StringWriter();
			final PrintWriter printWriter = new PrintWriter(stringWriter);
			exception.printStackTrace(printWriter);
			returnValue = stringWriter.toString();
		}
		return returnValue;
	}
}
