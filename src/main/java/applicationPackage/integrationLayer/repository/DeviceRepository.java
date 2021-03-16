package applicationPackage.integrationLayer.repository;

import applicationPackage.integrationLayer.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findBySerialNumber(String serialNumber);

   List<Device> findAllByDeviceActivated(byte status);


}
