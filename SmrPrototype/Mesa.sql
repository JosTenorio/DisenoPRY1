CREATE TABLE [dbo].[Mesa]
(
	[Id] INT NOT NULL PRIMARY KEY IDENTITY, 
    [Orientacion] DECIMAL NOT NULL, 
    [Forma] INT NOT NULL, 
    [Nombre] NVARCHAR(50) NOT NULL, 
    [TamanoA] DECIMAL(18,4) NOT NULL, 
    [TamanoB] DECIMAL(18,4) NULL,
    [PosicionX] DECIMAL(18,4) NOT NULL,
    [PosicionY] DECIMAL(18,4) NOT NULL,
    CONSTRAINT AkMesa UNIQUE(Nombre),
    CONSTRAINT ChMesa CHECK (TamanoA >= 0 AND ISNULL(TamanoB,0) >= 0)
)
