// (C) 2022 uchicom
package com.uchicom.csve;

import com.uchicom.csve.util.CSVHelperTest;
import com.uchicom.csve.util.CSVReaderTest;
import com.uchicom.csve.util.CSVRowTest;
import com.uchicom.csve.util.StringCellInfoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  CSVHelperTest.class,
  CSVReaderTest.class,
  CSVRowTest.class,
  StringCellInfoTest.class
})
public class CsveSuite {}
