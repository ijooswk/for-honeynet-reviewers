package mymock;

import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteDatabase;

public class MockSQLiteStatement extends SQLiteStatement {

	public MockSQLiteStatement(SQLiteDatabase db) {
		super(db);
	}

	public void bindString(int index, String value) {
	}

	public long executeInsert() {
		return 1;
	}
}
