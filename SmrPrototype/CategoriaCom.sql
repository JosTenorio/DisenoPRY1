CREATE TABLE [dbo].[CategoriaCom]
(
	[Id] INT NOT NULL PRIMARY KEY IDENTITY, 
    [Nombre] NVARCHAR(50) NOT NULL,
    [IdCategoriaPadre] INT NULL, 
	CONSTRAINT FkSubCategoriaCom FOREIGN KEY (IdCategoriaPadre) REFERENCES CategoriaCom (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT AkCategoriaCom UNIQUE(Nombre)
)
