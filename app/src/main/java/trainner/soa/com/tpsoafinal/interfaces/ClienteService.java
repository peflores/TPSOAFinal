package trainner.soa.com.tpsoafinal.interfaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import trainner.soa.com.tpsoafinal.entidad.Arduino;

/**
 * Created by Eze on 08/11/2016.
 */
public interface ClienteService {

    @GET("test")
    Call<Arduino> getEstadoLuz();

}
