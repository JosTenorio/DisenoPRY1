CREATE TRIGGER [TrgUpdItem]
	ON [dbo].[Item]
	FOR INSERT,UPDATE
	AS
	IF (ROWCOUNT_BIG() = 0)
	RETURN;
	SET NOCOUNT ON
	IF EXISTS (SELECT 1 FROM inserted WHERE inserted.Pago = 1)
	BEGIN
		DELETE FROM Orden WHERE Orden.Id IN	
		(SELECT Orden.Id
		FROM Item INNER JOIN Orden ON Item.IdOrden = Orden.Id
		WHERE Orden.Id IN (SELECT IdOrden FROM inserted WHERE inserted.Pago = 1 GROUP BY inserted.IdOrden)
		GROUP BY Orden.Id
		HAVING MIN(CAST(Pago AS TINYINT)) = 1)
	END;