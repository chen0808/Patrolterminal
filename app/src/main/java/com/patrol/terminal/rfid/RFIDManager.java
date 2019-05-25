package com.patrol.terminal.rfid;

import com.external.rfid.IRfid;
import com.external.rfid.RfidImpl;

/**
 * RFID 管理类
 */
public class RFIDManager {
	private static IRfid mRfid;

	public static IRfid getRFIDInstance() {
		if (mRfid == null) {
			mRfid = RfidImpl.getInstance();
		}
		return mRfid;
	}
}
