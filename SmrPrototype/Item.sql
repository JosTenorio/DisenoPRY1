CREATE TABLE [dbo].[Item]
(
	[Id] INT NOT NULL IDENTITY, 
    [Pago] BIT NOT NULL, 
    [Estado] BIT NOT NULL, 
    [Nota] NVARCHAR(50) NOT NULL, 
    [IdOrden] INT NOT NULL,
    CONSTRAINT FkItemOrden FOREIGN KEY (IdOrden) REFERENCES Orden(Id),
    CONSTRAINT PkItem PRIMARY KEY (IdOrden, Id)
)
