EXEC sp_rename 'dbo.SchedualCampaign', 'ScheduleCampaign';
GO

-- Đổi tên khóa chính và các ràng buộc liên quan
EXEC sp_rename 'PK_SchedualCampaign', 'PK_ScheduleCampaign', 'OBJECT';
GO

-- Cập nhật các ràng buộc khóa ngoại liên quan
ALTER TABLE [dbo].[SchedualEmployee] DROP CONSTRAINT [FK_SchedualEmployee_SchedualCampaign];
GO

ALTER TABLE [dbo].[SchedualEmployee] ADD CONSTRAINT [FK_SchedualEmployee_ScheduleCampaign] FOREIGN KEY([ScID])
REFERENCES [dbo].[ScheduleCampaign] ([ScID]);
GO