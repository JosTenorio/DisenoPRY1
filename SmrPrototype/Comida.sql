CREATE TABLE [dbo].[Comida]
(
	[Id] INT NOT NULL PRIMARY KEY IDENTITY, 
    [Descripcion] NVARCHAR(200) NOT NULL, 
    [DireccionFoto] NVARCHAR(200) NOT NULL, 
    [Precio] DECIMAL NOT NULL, 
    [Nombre] NVARCHAR(50) NOT NULL, 
    [Archivado] BIT NOT NULL, 
    [CantidadAcomp] INT NULL,
    [IdCategoria] INT NULL, 
    CONSTRAINT FkComidaCategoria FOREIGN KEY (IdCategoria) REFERENCES CategoriaCom (Id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT AkComida UNIQUE(Nombre),
    CONSTRAINT CkComidaPrecio CHECK (Precio >= 0),
    CONSTRAINT CkComidaCantAcomp CHECK (ISNULL(CantidadAcomp,0) >= 0)
)
