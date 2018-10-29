package pt.onept.dropmusic.common.communication.protocol;

public enum Operation {
	REGISTER,               //Self
	LOGIN,                  //Self -> Self
	CREATE,                 //Self, music | album | artist
	READ,                   //Self, music | album | artist
	UPDATE,                 //Self, music | album | artist
	UPDATE_USER,
	DELETE,                 //Self, music | album | artist
	SEARCH,                 //Self,  search String -> List<DropmusicDataType>
	ADD_REVIEW,             //Self, Review
	GET_NOTIFICATIONS,      //Self -> List<DropmusicDataType>
	NOTIFY,                 //UserToNotify, notification
	DELETE_NOTIFICATIONS,   //Self, lastSeenNotificationId
	UPLOAD_MUSIC,           //Self, Upload -> Upload
	DOWNLOAD_MUSIC,         //Self, FileId -> Upload
	SHARE_MUSIC,            //Self, FileId, UserShare

	SUCCESS,
	DUPLICATE,
	NOT_FOUND,
	INCOMPLETE,
	NO_PERMIT, //Where's your loicence m8?? 👮 👮 👮
	EXCEPTION


}
