USE [master]
GO
/****** Object:  Database [production_scheduling]    Script Date: 11/4/2024 12:53:21 AM ******/
CREATE DATABASE [production_scheduling]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'production_scheduling', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\production_scheduling.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'production_scheduling_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\production_scheduling_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [production_scheduling] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [production_scheduling].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [production_scheduling] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [production_scheduling] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [production_scheduling] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [production_scheduling] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [production_scheduling] SET ARITHABORT OFF 
GO
ALTER DATABASE [production_scheduling] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [production_scheduling] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [production_scheduling] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [production_scheduling] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [production_scheduling] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [production_scheduling] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [production_scheduling] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [production_scheduling] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [production_scheduling] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [production_scheduling] SET  DISABLE_BROKER 
GO
ALTER DATABASE [production_scheduling] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [production_scheduling] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [production_scheduling] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [production_scheduling] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [production_scheduling] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [production_scheduling] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [production_scheduling] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [production_scheduling] SET RECOVERY FULL 
GO
ALTER DATABASE [production_scheduling] SET  MULTI_USER 
GO
ALTER DATABASE [production_scheduling] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [production_scheduling] SET DB_CHAINING OFF 
GO
ALTER DATABASE [production_scheduling] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [production_scheduling] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [production_scheduling] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [production_scheduling] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'production_scheduling', N'ON'
GO
ALTER DATABASE [production_scheduling] SET QUERY_STORE = ON
GO
ALTER DATABASE [production_scheduling] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [production_scheduling]
GO
/****** Object:  Table [dbo].[Attendence]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Attendence](
	[AttendentID] [int] NOT NULL,
	[SchEmpID] [int] NOT NULL,
	[Quantity] [decimal](18, 2) NOT NULL,
	[Alpha] [float] NOT NULL,
 CONSTRAINT [PK_Attendence] PRIMARY KEY CLUSTERED 
(
	[AttendentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Department]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Department](
	[DepartmentID] [int] NOT NULL,
	[DepartmentName] [nvarchar](150) NOT NULL,
	[type] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_Department] PRIMARY KEY CLUSTERED 
(
	[DepartmentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[EmployeeID] [int] NOT NULL,
	[EmployeeName] [nvarchar](150) NOT NULL,
	[gender] [bit] NOT NULL,
	[address] [varchar](150) NOT NULL,
	[dob] [date] NOT NULL,
	[RoleID] [int] NOT NULL,
	[DepartmentID] [int] NOT NULL,
	[salary] [decimal](18, 2) NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[EmployeeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feature]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feature](
	[FeatureID] [int] NOT NULL,
	[FeatureName] [nvarchar](150) NOT NULL,
	[url] [varchar](max) NOT NULL,
 CONSTRAINT [PK_Feature] PRIMARY KEY CLUSTERED 
(
	[FeatureID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Plan]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Plan](
	[PlanID] [int] NOT NULL,
	[PlanName] [nvarchar](150) NOT NULL,
	[StartDate] [date] NOT NULL,
	[EndDate] [date] NOT NULL,
	[Quantity] [int] NOT NULL,
	[DepartmentID] [int] NOT NULL,
 CONSTRAINT [PK_Plan] PRIMARY KEY CLUSTERED 
(
	[PlanID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PlanCampain]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PlanCampain](
	[PlanCampnID] [int] NOT NULL,
	[PlanID] [int] NOT NULL,
	[ProductID] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Estimate] [int] NOT NULL,
 CONSTRAINT [PK_PlanCampain] PRIMARY KEY CLUSTERED 
(
	[PlanCampnID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[ProductID] [int] NOT NULL,
	[ProductName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[RoleID] [int] IDENTITY(1,1) NOT NULL,
	[RoleName] [nvarchar](150) NOT NULL,
	[Description] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RoleFeature]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RoleFeature](
	[FeatureID] [int] NOT NULL,
	[RoleID] [int] NOT NULL,
 CONSTRAINT [PK_RoleFeature] PRIMARY KEY CLUSTERED 
(
	[FeatureID] ASC,
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SchedualCampaign]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SchedualCampaign](
	[ScID] [int] NOT NULL,
	[PlanCampnID] [int] NOT NULL,
	[Date] [date] NOT NULL,
	[Shift] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
 CONSTRAINT [PK_SchedualCampaign] PRIMARY KEY CLUSTERED 
(
	[ScID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SchedualEmployee]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SchedualEmployee](
	[SchEmpID] [int] NOT NULL,
	[ScID] [int] NOT NULL,
	[EmployeeID] [int] NOT NULL,
	[Quantity] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_SchedualEmployee] PRIMARY KEY CLUSTERED 
(
	[SchEmpID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[UserName] [nvarchar](150) NOT NULL,
	[password] [varchar](150) NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserRole]    Script Date: 11/4/2024 12:53:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRole](
	[UserName] [nvarchar](150) NOT NULL,
	[RoleID] [int] NOT NULL,
 CONSTRAINT [PK_UserRole] PRIMARY KEY CLUSTERED 
(
	[UserName] ASC,
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Attendence]  WITH CHECK ADD  CONSTRAINT [FK_Attendence_SchedualEmployee] FOREIGN KEY([SchEmpID])
REFERENCES [dbo].[SchedualEmployee] ([SchEmpID])
GO
ALTER TABLE [dbo].[Attendence] CHECK CONSTRAINT [FK_Attendence_SchedualEmployee]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_Department] FOREIGN KEY([DepartmentID])
REFERENCES [dbo].[Department] ([DepartmentID])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_Department]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_Role] FOREIGN KEY([RoleID])
REFERENCES [dbo].[Role] ([RoleID])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_Role]
GO
ALTER TABLE [dbo].[Plan]  WITH CHECK ADD  CONSTRAINT [FK_Plan_Department1] FOREIGN KEY([DepartmentID])
REFERENCES [dbo].[Department] ([DepartmentID])
GO
ALTER TABLE [dbo].[Plan] CHECK CONSTRAINT [FK_Plan_Department1]
GO
ALTER TABLE [dbo].[PlanCampain]  WITH CHECK ADD  CONSTRAINT [FK_PlanCampain_Plan] FOREIGN KEY([PlanID])
REFERENCES [dbo].[Plan] ([PlanID])
GO
ALTER TABLE [dbo].[PlanCampain] CHECK CONSTRAINT [FK_PlanCampain_Plan]
GO
ALTER TABLE [dbo].[PlanCampain]  WITH CHECK ADD  CONSTRAINT [FK_PlanCampain_Product] FOREIGN KEY([ProductID])
REFERENCES [dbo].[Product] ([ProductID])
GO
ALTER TABLE [dbo].[PlanCampain] CHECK CONSTRAINT [FK_PlanCampain_Product]
GO
ALTER TABLE [dbo].[RoleFeature]  WITH CHECK ADD  CONSTRAINT [FK_RoleFeature_Feature] FOREIGN KEY([FeatureID])
REFERENCES [dbo].[Feature] ([FeatureID])
GO
ALTER TABLE [dbo].[RoleFeature] CHECK CONSTRAINT [FK_RoleFeature_Feature]
GO
ALTER TABLE [dbo].[RoleFeature]  WITH CHECK ADD  CONSTRAINT [FK_RoleFeature_Role] FOREIGN KEY([RoleID])
REFERENCES [dbo].[Role] ([RoleID])
GO
ALTER TABLE [dbo].[RoleFeature] CHECK CONSTRAINT [FK_RoleFeature_Role]
GO
ALTER TABLE [dbo].[SchedualCampaign]  WITH CHECK ADD  CONSTRAINT [FK_SchedualCampaign_PlanCampain] FOREIGN KEY([PlanCampnID])
REFERENCES [dbo].[PlanCampain] ([PlanCampnID])
GO
ALTER TABLE [dbo].[SchedualCampaign] CHECK CONSTRAINT [FK_SchedualCampaign_PlanCampain]
GO
ALTER TABLE [dbo].[SchedualEmployee]  WITH CHECK ADD  CONSTRAINT [FK_SchedualEmployee_Employee] FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[Employee] ([EmployeeID])
GO
ALTER TABLE [dbo].[SchedualEmployee] CHECK CONSTRAINT [FK_SchedualEmployee_Employee]
GO
ALTER TABLE [dbo].[SchedualEmployee]  WITH CHECK ADD  CONSTRAINT [FK_SchedualEmployee_SchedualCampaign] FOREIGN KEY([ScID])
REFERENCES [dbo].[SchedualCampaign] ([ScID])
GO
ALTER TABLE [dbo].[SchedualEmployee] CHECK CONSTRAINT [FK_SchedualEmployee_SchedualCampaign]
GO
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD  CONSTRAINT [FK_UserRole_Role] FOREIGN KEY([RoleID])
REFERENCES [dbo].[Role] ([RoleID])
GO
ALTER TABLE [dbo].[UserRole] CHECK CONSTRAINT [FK_UserRole_Role]
GO
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD  CONSTRAINT [FK_UserRole_User] FOREIGN KEY([UserName])
REFERENCES [dbo].[User] ([UserName])
GO
ALTER TABLE [dbo].[UserRole] CHECK CONSTRAINT [FK_UserRole_User]
GO
USE [master]
GO
ALTER DATABASE [production_scheduling] SET  READ_WRITE 
GO
