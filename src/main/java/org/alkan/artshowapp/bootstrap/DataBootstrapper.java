package org.alkan.artshowapp.bootstrap;

import org.alkan.artshowapp.models.Period;
import org.alkan.artshowapp.models.Style;
import org.alkan.artshowapp.models.artworks.*;
import org.alkan.artshowapp.models.people.Architect;
import org.alkan.artshowapp.models.people.Artist;
import org.alkan.artshowapp.repositories.artworks.ArchitectureRepository;
import org.alkan.artshowapp.repositories.artworks.MaterialRepository;
import org.alkan.artshowapp.repositories.artworks.SculptureRepository;
import org.alkan.artshowapp.repositories.people.ArchitectRepository;
import org.alkan.artshowapp.repositories.people.ArtistRepository;
import org.alkan.artshowapp.repositories.artworks.PaintingRepository;
import org.alkan.artshowapp.repositories.PeriodRepository;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Year;
import java.util.*;

@Component
public class DataBootstrapper implements CommandLineRunner {

    private final PaintingRepository paintings;
    private final StyleRepository styles;
    private final ArtistRepository artists;
    private final PeriodRepository periods;
    private final ArchitectureRepository architectures;
    private final SculptureRepository sculptures;
    private final ArchitectRepository architects;
    private final MaterialRepository materials;

    public DataBootstrapper(PaintingRepository paintings, StyleRepository styles, ArtistRepository artists,
                            PeriodRepository periods, ArchitectureRepository architectures,
                            SculptureRepository sculptures, ArchitectRepository architects,
                            MaterialRepository materials) {
        this.paintings = paintings;
        this.styles = styles;
        this.artists = artists;
        this.periods = periods;
        this.architectures = architectures;
        this.sculptures = sculptures;
        this.architects = architects;
        this.materials = materials;
    }

    @Override
    public void run(String... args) throws Exception {
//        createManually();
//        System.out.println(artists.findByName("sadfsafsad"));

        File folder = ResourceUtils.getFile("classpath:static/");
        System.out.println(folder); // /home/alkaor/spring/art-show-app/target/classes/static in localhost
        createFromCsv(folder + "/ArtShowData.csv");

//        Architecture architecture = Objects.requireNonNull(architectures.findById(1L).orElse(null));
    }

    private void createFromCsv(String csvFile) {
        Map<Long, String> paintingToAddArtist = new HashMap<>();
        Map<Long, String> sculptureToAddArtist = new HashMap<>();
        Map<Long, String> architectureToAddArchitects = new HashMap<>();

        try {
            Scanner inFile = new Scanner(new File(csvFile));

            String line;
            while (inFile.hasNextLine()) {
                line = inFile.nextLine();
                String[] cols = line.split(",");
                switch (cols[0]) {
                    case("1"):
                        Long paintingId = createPainting(cols[1], cols[2], cols[4], cols[5]);
                        paintingToAddArtist.put(paintingId, cols[3]);
                        break;
                    case("2"):
                        Long sculptureId = createSculpture(cols[1], cols[2], cols[4], cols[5]);
                        sculptureToAddArtist.put(sculptureId, cols[3]);
                        break;
                    case("3"):
                        Long architectureId = createArchitecture(cols[1], cols[2], cols[3], cols[4], cols[5]);
                        for(int i = 6; i < cols.length; i++)
                            architectureToAddArchitects.put(architectureId, cols[i]);
                        break;
                    case("4"):
                        createArtist(cols[1], cols[2], cols[3], cols[4],
                                Arrays.copyOfRange(cols, 5,cols.length - 1));
                        break;
                    case("5"):
                        createArchitect(cols[1], cols[2], cols[3], cols[4]);
                        break;
                    default:
                        break;
                }
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File couldn't be found");
        }

        assignArtistsToPaintings(paintingToAddArtist);
        assignArtistsToSculptures(sculptureToAddArtist);
        assignArchitectsToArchitectures(architectureToAddArchitects);
    }

    private void assignArtistsToPaintings(Map<Long, String> map) {
        for(Map.Entry<Long, String> entry : map.entrySet()) {
            Painting painting = paintings.findById(entry.getKey()).orElse(null);
            Artist artist = artists.findByName(entry.getValue());
            painting.setArtist(artist);
            paintings.save(painting);
        }
    }

    private void assignArtistsToSculptures(Map<Long, String> map) {
        for(Map.Entry<Long, String> entry : map.entrySet()) {
            Sculpture sculpture = sculptures.findById(entry.getKey()).orElse(null);
            Artist artist = artists.findByName(entry.getValue());
            sculpture.setArtist(artist);
            sculptures.save(sculpture);
        }
    }

    private void assignArchitectsToArchitectures(Map<Long, String> map) {
        for(Map.Entry<Long, String> entry : map.entrySet()) {
            Architecture architecture = architectures.findById(entry.getKey()).orElse(null);
            Architect architect = architects.findByName(entry.getValue());
            architecture.setArchitect(architect);
            architectures.save(architecture);
        }
    }

    private Long createPainting(String name, String styleName, String dim1, String dim2) {
        Painting painting = new Painting();
        painting.setName(name);
        Style style = createOrFindStyle(styleName);

        try {
            painting.setLength(Float.parseFloat(dim1));
            painting.setWidth(Float.parseFloat(dim2));
        } catch (Exception e){
            System.out.println("Invalid weight");
        }

        painting = paintings.save(painting);
        addStyleToArtwork(style, painting);
        return painting.getId();
    }

    private Long createSculpture(String name, String styleName, String materialName, String weight) {
        Sculpture sculpture = new Sculpture();
        sculpture.setName(name);
        Style style = createOrFindStyle(styleName);
        Material material = createOrFindMaterial(materialName);

        try {
            sculpture.setWeight(Integer.parseInt(weight));
        } catch (Exception e){
            System.out.println("Invalid weight");
        }

        sculpture = sculptures.save(sculpture);
        addStyleToArtwork(style, sculpture);
        addMaterialToSculpture(material, sculpture);
        return sculpture.getId();
    }

    private Long createArchitecture(String name, String styleName, String dim1, String dim2, String dim3) {
        Architecture architecture = new Architecture();
        architecture.setName(name);
        Style style = createOrFindStyle(styleName);

        try {
            architecture.setLength(Double.parseDouble(dim1));
            architecture.setWidth(Double.parseDouble(dim2));
            architecture.setHeight(Double.parseDouble(dim3));
        } catch (Exception e){
            System.out.println("One of the dimensions can not be converted to double");
        }

        architecture = architectures.save(architecture);
        addStyleToArtwork(style, architecture);
        return architecture.getId();
    }

    private Long createArtist(String name, String born, String died, String nationality, String[] periods) {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setNationality(nationality);
        Set<Period> periodArray = new HashSet<>();

        try {
            artist.setBornYear(Year.of(Integer.parseInt(born)));
            artist.setDeathYear(Year.of(Integer.parseInt(died)));
        } catch (Exception e) {
            System.out.println("Either death year or born year is not a decimal number.");
        }

        artist = artists.save(artist);

        for (String pName : periods)
            periodArray.add(createOrFindPeriod(pName));
        for (Period period : periodArray)
            addPeriodToArtist(period, artist);

        return artist.getId();
    }

    private Long createArchitect(String name, String born, String died, String nationality) {
        Architect architect = new Architect();
        architect.setName(name);
        architect.setNationality(nationality);
        try {
            architect.setBornYear(Year.of(Integer.parseInt(born)));
            architect.setDeathYear(Year.of(Integer.parseInt(died)));
        } catch (Exception e) {
            System.out.println("Either death year or born year is not a decimal number.");
        }
        architect = architects.save(architect);
        return architect.getId();
    }

    private void addStyleToArtwork(Style style, Artwork artwork) {
        artwork.setStyle(style);
        if (artwork instanceof Architecture)
            architectures.save((Architecture) artwork);
//          style.getArtworks().add(architecture);
//          styles.save(style);
        else if (artwork instanceof Sculpture)
            sculptures.save((Sculpture) artwork);
        else if (artwork instanceof Painting)
            paintings.save((Painting) artwork);
        else
            System.out.println("Whaat??");
    }

    private void addMaterialToSculpture(Material material, Sculpture sculpture) {
        sculpture.setMaterial(material);
        sculptures.save(sculpture);
    }

    private void addPeriodToArtist(Period period, Artist artist) {
        period.getArtists().add(artist);
        periods.save(period);
    }

    private Style createOrFindStyle(String name) {
        Style style = styles.findByName(name);
        if (style == null){
            style = new Style();
            style.setName(name);
            styles.save(style);
        }
        return style;
    }

    private Material createOrFindMaterial(String name) {
        Material material = materials.findByName(name);
        if (material == null){
            material = new Material();
            material.setName(name);
            materials.save(material);
        }
        return material;
    }

    private Period createOrFindPeriod(String name) {
        Period period = periods.findByName(name);
        if (period == null) {
            period = new Period();
            period.setName(name);
            periods.save(period);
        }
        return period;
    }

    private void createManually() {
        Style ren = new Style();
        ren.setName("Renaissance");
        ren.setId(1L);
        styles.save(ren);

        Style bar = new Style();
        bar.setName("Baroque");
        bar.setId(2L);
        styles.save(bar);

        Style got = new Style();
        got.setName("Gothic");
        got.setId(3L);
        styles.save(got);

        Period period1 = new Period();
        period1.setId(1L);
        period1.setName("Romanticism");

        Period period2 = new Period();
        period2.setId(2L);
        period2.setName("Modern");

        Period period3 = new Period();
        period3.setId(3L);
        period3.setName("Renaissance");

        Period period4 = new Period();
        period4.setId(4L);
        period4.setName("Medieval");

        periods.save(period1);
        periods.save(period2);
        periods.save(period3);
        periods.save(period4);

        Set<Period> periods1 = new HashSet<>();
        periods1.add(period1);

        Set<Period> periods2 = new HashSet<>();
        periods2.add(period1);
        periods2.add(period2);

        artists.save(Artist.builder().id(1L).bornYear(Year.of(1999))
                .bornYear(Year.of(1452))
                .deathYear(Year.of(1519))
                .name("Leonardo da Vinci").periods(periods1).nationality("Italy").build());
        artists.save(Artist.builder().id(2L).
                bornYear(Year.of(1999)).
                deathYear(Year.now()).
                name("Edvard Munch").periods(periods2).nationality("Norway").build());
        artists.save(Artist.builder().id(3L)
                .bornYear(Year.of(1863))
                .deathYear(Year.of(1944))
                .nationality("Norway")
                .name("Picasso").periods(periods1).build());
        artists.save(Artist.builder().id(4L)
                .bornYear(Year.of(1999))
                .nationality("Finland")
                .name("Someone Alive").periods(periods2).build());

        Artist artist1 = artists.findById(1L).orElse(null);
        Artist artist2 = artists.findById(2L).orElse(null);
        Artist artist3 = artists.findById(3L).orElse(null);
        Artist artist4 = artists.findById(4L).orElse(null);

        paintings.save(new Painting(1L, "name1", ren, 12.72f, 24.3f, artist1));
        paintings.save(new Painting(2L, "name2", bar, 13.72f, 24.3f, artist2));
        paintings.save(new Painting(3L, "name3", ren, 14.72f, 24.3f, artist3));
        paintings.save(new Painting(4L, "name4", bar, 15.72f, 24.3f, artist4));
        paintings.save(new Painting(5L, "name5", ren, 16.72f, 24.3f, artist1));

        Set<Artist> allArtists = new HashSet<>();
        allArtists.add(artist1);
        allArtists.add(artist2);
        allArtists.add(artist3);
        allArtists.add(artist4);

        Set<Artist> artistsDuble = new HashSet<>();
        artistsDuble.add(artist2);
        artistsDuble.add(artist4);

        period1.setArtists(allArtists);
        period2.setArtists(artistsDuble);

        periods.save(period1);
        periods.save(period2);

        Architecture architecture;
        for (long i = 1; i<10; i++) {
            architecture = new Architecture();
            architecture.setId(i);
            architecture.setName("Arch" + i);
            architecture.setWidth(12);
            architecture.setHeight(6);
            architecture.setLength(63);
            architectures.save(architecture);
        }

        Sculpture sculpture;
        for (long i = 1; i<10; i++) {
            sculpture = new Sculpture();
            sculpture.setId(i);
            sculpture.setName("Sculpture " + i);
            sculpture.setWeight((int) (500 * i));
            sculptures.save(sculpture);
        }



        Set<Architecture> allArchitectures = new HashSet<>();
        for(Architecture a : architectures.findAll())
            allArchitectures.add(a);

        Architect mainArch = new Architect();
        mainArch.setName("Main Arch");
        mainArch.setNationality("Norway");
        Architect savedArchitect = architects.save(mainArch);
        for(Architecture a : allArchitectures) {
            a.setArchitect(savedArchitect);
            architectures.save(a);
        }
        mainArch.setArchitectures(allArchitectures);
        savedArchitect = architects.save(mainArch);


        Architect architect;
        for (long i = 1; i<10; i++) {
            architect = new Architect();
            architect.setName("Architect" + i);
            architects.save(architect);
        }



        Material material;
        for (long i = 1; i<10; i++) {
            material = new Material();
            material.setName("Material" + i);
            materials.save(material);
        }
    }
}
