package org.bana.core.exception;

public class BanaCoreException extends RuntimeException
{
  private static final long serialVersionUID = 549835414253539012L;

  public BanaCoreException()
  {
  }

  public BanaCoreException(String arg0)
  {
    super(arg0);
  }

  public BanaCoreException(Throwable arg0)
  {
    super(arg0);
  }

  public BanaCoreException(String arg0, Throwable arg1)
  {
    super(arg0, arg1);
  }
}