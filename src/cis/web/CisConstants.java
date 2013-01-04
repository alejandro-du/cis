package cis.web;

import enterpriseapp.Utils;
import enterpriseapp.ui.Constants;


public abstract class CisConstants extends Constants {
	
	public static final String roleCustomer = Utils.getProperty("db.roleCustomer");
	public static final String roleEmployee = Utils.getProperty("db.roleEmployee");
	
	public static final String emailSuggestionBoxEmail = Utils.getProperty("mail.suggestionBoxEmail");
	
	public static final String uiMainWindowCaption = Utils.getProperty("ui.mainWindowCaption");
	public static final String uiUserLogin = Utils.getProperty("ui.user.login");
	public static final String uiInitSession = Utils.getProperty("ui.initSession");
	public static final String uiUserPassword = Utils.getProperty("ui.user.password");
	public static final String uiBadCredentials = Utils.getProperty("ui.badCredrentials");
	public static final String uiUpdatePassword = Utils.getProperty("ui.updatePassword");
	public static final String uiRestorePassword = Utils.getProperty("ui.restorePassword");
	public static final String uiLogout = Utils.getProperty("ui.logout");
	public static final String uiProject = Utils.getProperty("ui.project");
	public static final String uiProjects = Utils.getProperty("ui.projects");
	public static final String uiTickets = Utils.getProperty("ui.tickets");
	public static final String uiFiles = Utils.getProperty("ui.files");
	public static final String uiCategories = Utils.getProperty("ui.categories");
	public static final String uiCustomer = Utils.getProperty("ui.customer");
	public static final String uiEmail = Utils.getProperty("ui.email");
	public static final String uiPhoneNumber = Utils.getProperty("ui.phoneNumber");
	public static final String uiCellPhoneNumber = Utils.getProperty("ui.cellPhoneNumber");
	public static final String uiUsers = Utils.getProperty("ui.users");
	public static final String uiInvalidEmail = Utils.getProperty("ui.invalidEmail");
	public static final String uiPasswordDoesNotMatch = Utils.getProperty("ui.passwordDoesNotMatch");
	public static final String uiPasswordSent = Utils.getProperty("ui.passwordSent");
	public static final String uiPasswordNotSent = Utils.getProperty("ui.passwordNotSent");
	public static final String uiRestore = Utils.getProperty("ui.restore");
	public static final String uiCurrentPassword = Utils.getProperty("ui.currentPassword");
	public static final String uiNewPassword = Utils.getProperty("ui.newPassword");
	public static final String uiNewPasswordConfirmation = Utils.getProperty("ui.newPasswordConfirmation");
	public static final String uiTheNewGeneratedPasswordIs = Utils.getProperty("ui.theNewGeneratedPasswordIs");
	public static final String uiUpdatePhone = Utils.getProperty("ui.updatePhone");
	public static final String uiUpdateCellPhone = Utils.getProperty("ui.updateCellPhone");
	public static final String uiFile = Utils.getProperty("ui.file");
	public static final String uiFileDate = Utils.getProperty("ui.file.date");
	public static final String uiFileDescription = Utils.getProperty("ui.file.description");
	public static final String uiFileSize = Utils.getProperty("ui.fileSize");
	public static final String uiUploadedFiles = Utils.getProperty("ui.uploadedFiles");
	public static final String uiAddAnother = Utils.getProperty("ui.addAnother");
	public static final String uiSuggestionBox = Utils.getProperty("ui.suggestionBox");
	public static final String uiSuggestionBoxDescription = Utils.getProperty("ui.suggestionBoxDescription");
	public static final String uiSend = Utils.getProperty("ui.send");
	public static final String uiClear = Utils.getProperty("ui.clear");
	public static final String uiSuggestionSent = Utils.getProperty("ui.suggestionSent");
	public static final String uiTicketsDescription = Utils.getProperty("ui.ticketsDescription");
	public static final String uiTicketTitle = Utils.getProperty("ui.ticketTitle");
	public static final String uiPriority = Utils.getProperty("ui.priority");
	public static final Object uiNormalPriority = Utils.getProperty("ui.normalPriority");
	public static final Object uiHighPriority = Utils.getProperty("ui.highPriority");
	public static final String uiTicketSent = Utils.getProperty("ui.ticketSent");
	public static final String uiTicketNotSent = Utils.getProperty("ui.ticketNotSent");
	public static final String uiSuggestionNotSent = Utils.getProperty("ui.suggestionNotSent");
	public static final String uiNoFiles = Utils.getProperty("ui.noFiles");
	public static final String uiNoClosedTickets = Utils.getProperty("ui.noClosedTickets");
	public static final String uiProjectLocked = Utils.getProperty("ui.projectLocked");
	public static final String uiComments = Utils.getProperty("ui.comments");
	public static final String uiClosedTickets = Utils.getProperty("ui.closedTickets");
	public static final String uiNewFileNotificationSubject = Utils.getProperty("ui.newFileNotificationSubject");
	public static final String uiNewFileNotificationMessage = Utils.getProperty("ui.newFileNotificationMessage");
	public static final String uiNewFileNotificationNotSent(String email) { return Utils.getProperty("ui.newFileNotificationNotSent", new String[] {email}); }
	
}
