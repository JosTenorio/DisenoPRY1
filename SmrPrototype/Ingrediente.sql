CREATE TABLE [dbo].[Ingrediente]
(
	[Id] INT NOT NULL PRIMARY KEY IDENTITY,
	[Nombre] NVARCHAR(50) NOT NULL, 
    [Descripcion] NCHAR(200) NOT NULL, 
    [Minimo] INT NOT NULL, 
    [Cantidad] INT NOT NULL, 
    [Medida] NVARCHAR(50) NOT NULL, 
    [DireccionFoto] NVARCHAR(200) NOT NULL, 
    [IdCategoria] INT NULL,
    CONSTRAINT FkIngredienteCategoria FOREIGN KEY (IdCategoria) REFERENCES CategoriaIng (Id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT AkIngrediente UNIQUE(Nombre),
    CONSTRAINT CkIngCantidad CHECK (Cantidad >= 0),
    CONSTRAINT CkIngMinimo CHECK (Minimo >= 0)
)
