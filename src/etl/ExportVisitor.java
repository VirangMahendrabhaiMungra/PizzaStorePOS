package etl;

public interface ExportVisitor {
    void visit(HistoricalOrder order);
    void finishExport();
}
