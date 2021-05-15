CREATE TABLE [dbo].[Mesa]
(
	[Id] INT NOT NULL PRIMARY KEY IDENTITY, 
    [Orientacion] DECIMAL NOT NULL, 
    [Forma] INT NOT NULL, 
    [Nombre] NVARCHAR(50) NOT NULL, 
    [TamanoA] DECIMAL NOT NULL, 
    [TamanoB] DECIMAL NULL,
    [PosicionX] DECIMAL NOT NULL,
    [PosicionY] DECIMAL NOT NULL,
    CONSTRAINT AkMesa UNIQUE(Nombre),
    CONSTRAINT ChMesa CHECK (TamanoA >= 0 AND ISNULL(TamanoB,0) >= 0)
)
