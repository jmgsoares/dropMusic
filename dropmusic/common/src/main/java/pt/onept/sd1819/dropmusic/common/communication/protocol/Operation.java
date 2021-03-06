package pt.onept.sd1819.dropmusic.common.communication.protocol;

/**
 * Enum of all the operations supported (to be some) by the application
 */
public enum Operation {
	REGISTER,               //Self
	LOGIN,                  //Self -> Self
	CREATE,                 //Self, music | album | artist
	READ,                   //Self, music | album | artist
	//TODO update and update_user -> can it be merged in one operation only?
	UPDATE,                 //Self, music | album | artist
	UPDATE_USER,
	//TODO delete operation needed?
	DELETE,                 //Self, music | album | artist
	CLEAN,                  //Self,
	LINK_FILE,              //Self, File
	SEARCH,                 //Self, search String -> List<DropmusicDataType>
	LIST,                   //Self, music | album | artist
	ADD_REVIEW,             //Self, Review
	GET_NOTIFICATIONS,      //Self -> List<DropmusicDataType>
	NOTIFY,                 //UserToNotify, notification
	DELETE_NOTIFICATIONS,   //Self, lastSeenNotificationId
	GET_EDITORS,            //Self, album | artist
	UPLOAD_MUSIC,           //Self, File -> File
	DOWNLOAD_MUSIC,         //Self, FileId -> File
	SHARE,                  //Self, FileId, UserShare
	LIST_SHARES,            //Self

	SUCCESS,
	DUPLICATE,
	NOT_FOUND,
	INCOMPLETE,
	NO_PERMIT, //Where's your loicence m8?? 👮 👮 👮
	EXCEPTION
}