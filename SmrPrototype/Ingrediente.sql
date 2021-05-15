CREATE TABLE [dbo].[Ingrediente]
(
	[Id] INT NOT NULL PRIMARY KEY IDENTITY,
	[Nombre] NVARCHAR(50) NOT NULL, 
    [Descripcion ] NCHAR(200) NOT NULL, 
    [Minimo] INT NOT NULL, 
    [Cantidad] INT NOT NULL, 
    [Medida] NVARCHAR(50) NOT NULL, 
    [DireccionFoto] NVARCHAR(200) NULL 
)
