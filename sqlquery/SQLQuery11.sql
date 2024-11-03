EXEC sp_rename 'dbo.User', 'AppUser';
GO

-- Đổi tên khóa chính và các ràng buộc liên quan
EXEC sp_rename 'PK_User', 'PK_AppUser', 'OBJECT';
GO

-- Cập nhật các ràng buộc khóa ngoại liên quan
ALTER TABLE [dbo].[UserRole] DROP CONSTRAINT [FK_UserRole_User];
GO

ALTER TABLE [dbo].[UserRole] ADD CONSTRAINT [FK_UserRole_AppUser] FOREIGN KEY([UserName])
REFERENCES [dbo].[AppUser] ([UserName]);
GO