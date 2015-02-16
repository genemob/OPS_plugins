package patmob.plugin.ops;

import patmob.core.PatmobPlugin;
import patmob.data.ops.impl.BiblioSearchRequest;
import patmob.data.ops.impl.CpcSearchRequest;
import patmob.data.ops.impl.EquivalentsRequest;

/**
 *
 * @author Piotr
 */
public class OpsPlugin implements PatmobPlugin {
    OpsFrame opsFrame;

    @Override
    public String getName() {
//        return "REST@OPS plugin";
        return "REST@OPS Plug-in";
    }

    public void biblioSearch(String queryString, int mode) {
        BiblioSearchRequest biblioSearch;
        switch (mode) {
            case (BiblioSearchRequest.SAMPLE_RESULT):
                biblioSearch = new BiblioSearchRequest(
                        queryString, opsFrame, coreAccess.getController(),
                        BiblioSearchRequest.SAMPLE_RESULT);
                biblioSearch.submit();
                break;
            case (BiblioSearchRequest.FULL_RESULT):
                biblioSearch = new BiblioSearchRequest(
                        queryString, opsFrame, coreAccess.getController(),
                        BiblioSearchRequest.FULL_RESULT);
                biblioSearch.submit();
        }
    }
    
    public void cpcSearch(String cpcQuery) {
        CpcSearchRequest cpcSearch = new CpcSearchRequest(cpcQuery, opsFrame);
        cpcSearch.submit();
    }
    
    @Override
    public void doJob() {
        opsFrame = new OpsFrame(this);
        opsFrame.setVisible(true);
    }
    
}