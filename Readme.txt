ArReader - R.Ryffel 6/18/2015
ArReader is a java application that reads xml files and creates output records.

Currently there are 4 packages structures:
com.clc.arreader.beans - Contains Output Bean Object
com.clc.arreader.fileprocessors - Contains FileReader Objects
com.clc.arreader.main -Contains main driver
com.clc.arreader.util - Contains Utility objects

ArReader Output Record - 
ARTitleOutputBean - manages the ArReader Output record format which combines filed from the AR_Titles.xml 
and AR_Titles_ISBN.xml file
Output record format -
iBook vchEAN iQuizID iReadingLevel vchInterestLevel iARPoints

XML files - 
Ar_Titles.xml - ArTitlesReader extends ArReader
Ar_Titles_ISBN.xml - ArTitilesIsbnReader extends ArReader

Utility files -
FileUtility - handles reading, writing, creating, and deleting existing files.
UniversalNamsepaceCache - handle Namespace mapping within the xml files for Xpath processing.

Main driver - ArReaderDriver
Driver will read every ibook (datarow) from the Ar_Titles.xml with a QuizType="RP" and extract the following fields:
iBook 
iQuizID 
iReadingLevel 
vchInterestLevel
iARPoints

Will then proceed to read the AR_Titles_ISBN.xml file using ibook and vchEAN that starts with "978*"
Will select the vchEAN fild for each of the selected rows on the table.

Output is written to the console along with certain system application log messages.
Output records are written from the ARTitleOutputBean.
The are also written to a ArOutput.txt file that maintains a timestamp in the header as well.
When the driver starts it will check to see if an ArOutput.txt files exists and if it does it deletes it then creates a new one with a header record.
Each record is writting on its on line.

Application issue to be made aware of:
1. # symbol in the namespaces of the xml files will cause the following error:
javax.xml.transform.TransformerException: A location step was expected following the '/' or '//' token. 
Solution - Remove the # from the following namespace -
xmlns:z='#RowsetSchema'>

2. Out of memory Java heap space error. These xml files in their full range are to big for certain platforms. 
This application does not currently us any caching solutions.
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
Solution - Run on machine with sufficient memory to process such large files.
