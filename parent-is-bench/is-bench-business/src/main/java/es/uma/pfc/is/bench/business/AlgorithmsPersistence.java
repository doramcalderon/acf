//package es.uma.pfc.is.bench.business;
//
//import es.uma.pfc.is.bench.domain.AlgorithmEntity;
//import es.uma.pfc.is.bench.domain.Algorithms;
//import es.uma.pfc.is.bench.domain.Workspace;
//import es.uma.pfc.is.commons.files.FileUtils;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//import javax.xml.bind.JAXB;
//
///**
// * Persist the algorithms into an XML file entities using JAXB.
// *
// * @author Dora Calderón
// */
//public class AlgorithmsPersistence {
//    
//
//    /**
//     * Single instance.
//     */
//    private static AlgorithmsPersistence me;
//
//    /**
//     * Constructor.
//     */
//    private AlgorithmsPersistence() {
//    }
//
//    /**
//     * Get a single instance of AlgorithmsPersistence.
//     *
//     * @return AlgorithmsPersistence.
//     */
//    public static AlgorithmsPersistence get() {
//        synchronized(AlgorithmsPersistence.class) {
//            if (me == null) {
//                me = new AlgorithmsPersistence();
//            }
//        }
//        return me;
//    }
//
//   
//
//    /**
//     * Add the algorithms of {@code algorithms} param to algorithms file.
//     *
//     * @param workspace Workspace path.
//     * @param algorithms
//     */
//    public void insert(String workspace, AlgorithmEntity ... algorithms) {
//        if (algorithms == null) {
//            throw new IllegalArgumentException("algorithms argument can't be null.");
//        }
//        
//        Set<AlgorithmEntity> algs = getAlgorithmsCatalog(workspace);
//        if (algs == null) {
//            algs = new HashSet<>();
//        }
//        algs.addAll(Arrays.asList(algorithms));
//        
//        
////        
////TODO        JAXB.marshal(algs, UserConfig.get().getAlgorithmsFile());
//    }
//
//    
//    
//    
//    /**
//     * Delete an algorithm from algorithms file.
//     *
//     * @param algorithm Algorithm.
//     */
//    public void delete(AlgorithmEntity algorithm) {
//        if (algorithm == null) {
//            throw new IllegalArgumentException("algorithm param can't be null.");
//        }
//        //TODO
////        Algorithms algs = getAlgorithms();
////        if(algs.getAlgorithms() != null) {
////            algs.getAlgorithms().remove(algorithm);
////        }
////        create(algs);
//    }
//    
//    public void clear() {
////        create(new Algorithms());
//    }
//
//    
//    /**
//     * Returns the algorithms catalog of a workspace.
//     * @param workspacePath Workspace path.
//     * @return Algorithms catalog of a workspace.<br/> {@code null} if the workspacePath not exists or algorithms 
//     * in workspace not found.
//     */
//    protected Set<AlgorithmEntity> getAlgorithmsCatalog(String workspacePath) {
//        Set<AlgorithmEntity> algorithms = null;
//        Workspace ws = WorkspacePersistence.getWorkspace(workspacePath);
//        if(ws != null) {
//            algorithms = ws.getAlgorithms();
//        }
//        return algorithms;
//                
//    }
//
//}
