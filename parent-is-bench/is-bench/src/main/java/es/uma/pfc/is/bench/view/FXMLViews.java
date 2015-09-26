
package es.uma.pfc.is.bench.view;

/**
 * Views' roots.
 * @author Dora Calderón
 */
public interface FXMLViews {
    /** Root view.**/
    // String ROOT_VIEW = "/fxml/Root.fxml";
    String ROOT_VIEW = "/es/uma/pfc/is/bench/MainLayout.fxml";
    
    /** Benchmarks dashboard view. **/
    String BENCHMARKS_DASHBOARD_VIEW = "/es/uma/pfc/is/bench/home/Home.fxml";
    
    /** Run Benchmark view. **/
    String RUN_BENCHMARK_VIEW = "/es/uma/pfc/is/bench/benchmarks/execution/RunBenchmark.fxml";
    
    /** File viewer view.**/
    String FILE_VIEWER_VIEW = "/es/uma/pfc/is/bench/benchmarks/execution/FileViewer.fxml";
    /** Results viewer view.**/
    String RESULTS_VIEWER_VIEW = "/es/uma/pfc/is/bench/benchmarks/results/ResultsViewer.fxml";
    
    /** Algorithms view. **/
    String ALGORITHMS_VIEW = "/es/uma/pfc/is/bench/algorithms/Algorithms.fxml";
    
    /** New Benchmark view.**/
    String NEW_BENCHMARK_VIEW = "/es/uma/pfc/is/bench/benchmarks/newbm/NewBenchmark.fxml";


    
    /**
     * Results view.
     */
    String RESULTS_VIEW = "/es/uma/pfc/is/bench/benchmarks/results/Results.fxml";
    
    /** User Preferences view. **/
    String USER_CONFIG_VIEW = "/es/uma/pfc/is/bench/config/UserConfig.fxml";
    /** About view. **/
    String ABOUT_VIEW = "/es/uma/pfc/is/bench/About.fxml";
}
