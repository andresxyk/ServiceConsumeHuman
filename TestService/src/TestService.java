import com.mx.lacomer.restws.servicethird.ConsumoServiciosThird;

/**
 * 
 */

/**
 * @author soporte
 *
 */
public class TestService {
	public static void main(String[] args) {
		try {
			String signatureCoordinates = "[{  \"page\": 1,  \"x\": 5,  \"y\": 5,  \"width\": 5,  \"height\": 5}]";
			ConsumoServiciosThird.consumeService("test-5.pdf", "https://api-prod.humand.co/public/api/v1/users/742506043/documents/files",
					"c4dpS-0u-L-e9TCa04DYlT6NfSagKQYr", Integer.valueOf(9221), signatureCoordinates,
					Boolean.valueOf(false), "SIGNATURE_NOT_NEEDED", Boolean.valueOf(false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
