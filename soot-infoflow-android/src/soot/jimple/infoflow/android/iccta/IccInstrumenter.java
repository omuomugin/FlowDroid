package soot.jimple.infoflow.android.iccta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soot.jimple.infoflow.handlers.PreAnalysisHandler;

import java.util.List;

public class IccInstrumenter implements PreAnalysisHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String iccModel;

    public IccInstrumenter(String iccModel) {
        this.iccModel = iccModel;
    }

    @Override
    public void onBeforeCallgraphConstruction() {
        logger.info("[IccTA] Launching IccTA Transformer...");

        logger.info("[IccTA] Loading the ICC Model...");
        Ic3Provider provider = new Ic3Provider(iccModel);
        List<IccLink> iccLinks = provider.getIccLinks();
        logger.info("[IccTA] ...End Loading the ICC Model");

        logger.info("[IccTA] Lauching ICC Redirection Creation...");
        for (IccLink link : iccLinks) {
            if (link.fromU == null) {
                continue;
            }
            IccRedirectionCreator.v().redirectToDestination(link);
        }
        logger.info("[IccTA] ...End ICC Redirection Creation");
    }

    @Override
    public void onAfterCallgraphConstruction() {
        //
    }
}
