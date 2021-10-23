package ru.job4j.cars.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.entity.*;
import ru.job4j.cars.persistence.AdRepository;
import ru.job4j.cars.persistence.HbmStorage;
import ru.job4j.cars.persistence.Storage;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

public class Cars {
    private final Storage storage = HbmStorage.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(Cars.class.getName());

    private Cars() {
    }

    private static final class Lazy {
        private static final Cars INST = new Cars();
    }

    public static Cars getInstance() {
        return Cars.Lazy.INST;
    }

    public static Logger getLogger() {
        return LOG;
    }

    public void saveAdvertisement(final Advertisement ad) throws SQLException {
        try {
            ad.setCreated(new Date());
            Optional<User> userOptional = Optional.of(
                    HbmStorage.getInstance().findUserByLogin(ad.getOwner().getLogin())
            );
            ad.setOwner(userOptional.get());
            storage.saveAdvertisement(ad);
        } catch (SQLException exception) {
            LOG.error("SQL Exception: " + exception.getMessage(), exception);
            throw exception;
        } catch (NoSuchElementException exception) {
            LOG.error("No Such Element Exception: " + exception.getMessage(), exception);
            throw exception;
        }
    }

    public void updateAdvertisement(final Advertisement ad) throws SQLException {
        try {
            storage.updateAdvertisement(ad);
        } catch (SQLException exception) {
            LOG.error("SQL Exception: " + exception.getMessage(), exception);
            throw exception;
        }
    }

    public Collection<Advertisement> getAllAdvertisements() throws SQLException {
        List<Advertisement> ads = null;
        try {
            ads = (List<Advertisement>) storage.getAllAdvertisements();
            if (ads != null) {
                ads.sort(Comparator.comparing(Advertisement::getCreated)
                        .thenComparing(Advertisement::getOwner)
                        .reversed());
            }
        } catch (SQLException exception) {
            LOG.error("SQL Exception: " + exception.getMessage(), exception);
            throw exception;
        }
        return ads;
    }

    public Advertisement findAdById(int id) throws SQLException {
        Advertisement ad = null;
        try {
            ad = storage.findAdById(id);
        } catch (SQLException exception) {
            LOG.error("SQL Exception: " + exception.getMessage(), exception);
            throw exception;
        }
        return ad;
    }

    public Collection<Advertisement> findAdBySold(boolean key) throws SQLException {
        List<Advertisement> ads = null;
        try {
            ads = (List<Advertisement>) storage.findAdBySold(key);
            if (ads != null) {
                ads.sort(Comparator.comparing(
                        Advertisement::getCreated
                ).thenComparing(Advertisement::getOwner).reversed());
            }
        } catch (SQLException exception) {
            LOG.error("SQL Exception: " + exception.getMessage(), exception);
            throw exception;
        }
        return ads;
    }

    public User findUserByLogin(String login) throws SQLException {
        User user = null;
        try {
            user = HbmStorage.getInstance().findUserByLogin(login);
        } catch (SQLException exception) {
            LOG.error("SQL Exception: " + exception.getMessage(), exception);
            throw exception;
        }
        return user;
    }

    public User findUserByLoginAndPassword(String login, String password) throws SQLException {
        User user = null;
        try {
            user = HbmStorage.getInstance().findUserByLoginAndPassword(login, password);
        } catch (SQLException exception) {
            LOG.error("SQL Exception: " + exception.getMessage(), exception);
            throw exception;
        }
        return user;
    }

    public void saveUser(final User user) throws SQLException {
        try {
            storage.saveUser(user);
        } catch (SQLException exception) {
            LOG.error("SQL Exception: " + exception.getMessage(), exception);
            throw exception;
        }
    }

    public Collection<Brand> getAllBrands() {
        return HbmStorage.getInstance().getAllBrands();
    }

    public Map<String, Object> getFormSelect() {

        Map<String, Object> fieldsData = new HashMap<>();

        List<City> cities = (List<City>) HbmStorage.getInstance().getAllCites();
        fieldsData.put("cities", cities);

        List<Brand> brands = (List<Brand>) getAllBrands();
        fieldsData.put("brands", brands);

        List<Model> models = (List<Model>) HbmStorage.getInstance().getAllModels();
        fieldsData.put("models", models);

        List<BodyType> bodyTypes = (List<BodyType>) HbmStorage.getInstance().getAllBodyTypes();
        fieldsData.put("bodyTypes", bodyTypes);

        List<Engine> engines = (List<Engine>) HbmStorage.getInstance().getAllEngines();
        fieldsData.put("engines", engines);

        List<Transmission> transmissions =
                (List<Transmission>) HbmStorage.getInstance().getAllTransmissions();
        fieldsData.put("transmissions", transmissions);

        return fieldsData;
    }

    public Collection<Advertisement> getAdsWithFilter(String filter, int brandId) throws Exception {
        List<Advertisement> ads = null;
        filter = "".equals(filter) ? null : filter;
        try {
            if (Objects.nonNull(filter) && brandId == 0) {
                switch (filter) {
                    case "lastDayFilter":
                        ads = (List<Advertisement>) AdRepository.getInstance().getAdsByLastDay();
                        break;
                    case "withPhotoFilter":

                        ads = (List<Advertisement>) AdRepository.getInstance().getAdsWithPhoto();
                        break;
                    default:
                }
            } else if (Objects.isNull(filter) && brandId != 0) {
                ads = (List<Advertisement>) AdRepository.getInstance().getAdsByBrand(brandId);
            } else if (Objects.nonNull(filter) && brandId != 0) {
                switch (filter) {
                    case "lastDayFilter":
                        ads = (List<Advertisement>) AdRepository.getInstance()
                                .getAdsByLastDayAndBrand(brandId);
                        break;
                    case "withPhotoFilter":
                        ads = (List<Advertisement>) AdRepository.getInstance()
                                .getAdsWithPhotoAndByBrand(brandId);
                        break;
                    default:
                }
            }
        } catch (URISyntaxException exception) {
            LOG.error("URI Exception: " + exception.getMessage(), exception);
            throw exception;
        }
        return ads;
    }
}
