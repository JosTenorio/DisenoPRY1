CREATE TRIGGER [TrgInsAgregado]
	ON [dbo].[Agregado]
	FOR INSERT
	AS
	IF (ROWCOUNT_BIG() = 0)
	RETURN;
	SET NOCOUNT ON
	IF EXISTS ( SELECT 1
				FROM inserted INNER JOIN Item ON (inserted.IdItem = Item.Id)
				INNER JOIN Comida ON Item.IdComida = Comida.Id
				WHERE Comida.CantidadAcomp IS NULL
		)
	BEGIN  
		RAISERROR ('No se pueden incluir agregados en un item de acompañamiento', 16, 1);  
		ROLLBACK TRANSACTION;  
		RETURN   
	END;
