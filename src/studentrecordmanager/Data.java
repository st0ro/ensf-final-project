package studentrecordmanager;

/**
 * Represents data within a node
 */
public class Data {
	
	/**
	 * id is the student's id
	 * faculty is the student's faculty 
	 */
	String  id,faculty, major, year;
	
	/**
	 * Creates a data object with the provided arguments
	 * @param i id number
	 * @param f faculty
	 * @param m major
	 * @param y year
	 */
	public Data( String i, String f, String m, String y)
	{
		id = i;
		faculty = f;
		major = m;
		year = y;
	}
	
	@Override
	public String toString()
	{
		return ("id : " + id + " faculty: " + faculty + " major: " + major + 
						" year: " + year);
	}	

}
