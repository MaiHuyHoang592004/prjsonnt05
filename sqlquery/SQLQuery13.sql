EXEC sp_rename 'dbo.SchedualEmployee', 'ScheduleEmployee';
GO

-- Đổi tên khóa chính và các ràng buộc liên quan
EXEC sp_rename 'PK_SchedualEmployee', 'PK_ScheduleEmployee', 'OBJECT';
GO

-- Cập nhật các ràng buộc khóa ngoại liên quan
ALTER TABLE [dbo].[Attendance] DROP CONSTRAINT [FK_Attendence_SchedualEmployee];
GO

ALTER TABLE [dbo].[Attendance] ADD CONSTRAINT [FK_Attendence_ScheduleEmployee] FOREIGN KEY([SchEmpID])
REFERENCES [dbo].[ScheduleEmployee] ([SchEmpID]);
GO