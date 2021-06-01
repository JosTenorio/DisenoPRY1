﻿CREATE TABLE [dbo].[Agregado]
(
	[Id] INT NOT NULL IDENTITY, 
    [IdItem] INT NOT NULL, 
    [IdAcomp] INT NOT NULL,
    CONSTRAINT PkAgregado PRIMARY KEY (Id),
    CONSTRAINT FkAgregadoItem FOREIGN KEY (IdItem) REFERENCES Item (Id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FkAgregadoAcomp FOREIGN KEY (IdAcomp) REFERENCES Comida (Id) ON DELETE NO ACTION ON UPDATE CASCADE
)