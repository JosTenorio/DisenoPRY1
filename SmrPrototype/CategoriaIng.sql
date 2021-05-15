CREATE TABLE [dbo].[CategoriaIng]
(
	[Id] INT NOT NULL PRIMARY KEY IDENTITY, 
    [Nombre] NVARCHAR(50) NOT NULL,
	[IdCategoriaPadre] INT NULL, 
    CONSTRAINT FkSubCategoriaIng FOREIGN KEY (IdCategoriaPadre) REFERENCES CategoriaIng (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT AkCategoriaIng UNIQUE(Nombre)
)
