package com.InternetAndApplications.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/project")
public class MainController {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorsByCountryRepository authorsByCountryRepository;

    @Autowired
    private AuthorsBySettlementRepository authorsBySettlementRepository;

    @Autowired
    private AuthorsByCategoryRepository authorsByCategoryRepository;

    @Autowired
    private ArticlesByWordCategoryRepository articlesByWordCategoryRepository;

    @GetMapping(path="/authorsByCountry")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody ChartData getAuthorsByCountry() {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();

        //authorsByCountry
        Iterator<AuthorsByCountry> authorsByCountryIterator = authorsByCountryRepository.findAll().iterator();
        while (authorsByCountryIterator.hasNext()) {
            AuthorsByCountry a = authorsByCountryIterator.next();
            labels.add(a.getCountry());
            data.add(a.getCount());
        }
        ArrayList<String> tmp = new ArrayList<>();
        DataSet d = new DataSet("Authors by Country", data, tmp);
        ArrayList<DataSet> ld = new ArrayList<>();
        ld.add(d);
        ChartData chartData = new ChartData(labels, ld);
        return chartData;
    }

    @GetMapping(path="/authorsBySettlement")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody ChartData getAuthorsBySettlement() {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();

        //authorsBySettlement
        Iterator<AuthorsBySettlement> authorsBySettlementIterator = authorsBySettlementRepository.findAll().iterator();
        while (authorsBySettlementIterator.hasNext()) {
            AuthorsBySettlement a = authorsBySettlementIterator.next();
            labels.add(a.getSettlement());
            data.add(a.getCount());
        }
        ArrayList<String> tmp = new ArrayList<>();
        DataSet d = new DataSet("Authors by Settlement", data, tmp);
        ArrayList<DataSet> ld = new ArrayList<>();
        ld.add(d);
        ChartData chartData = new ChartData(labels, ld);
        return chartData;
    }

    @GetMapping(path="/authorsByCategory")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody ChartData getAuthorsByCategory() {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();

        //authorsByCategory
        Iterator<AuthorsByCategory> authorsByCategoryIterator = authorsByCategoryRepository.findAll().iterator();
        while (authorsByCategoryIterator.hasNext()) {
            AuthorsByCategory a = authorsByCategoryIterator.next();
            labels.add(a.getCategory());
            data.add(a.getCount());
        }
        ArrayList<String> tmp = new ArrayList<>();
        DataSet d = new DataSet("Articles Per Number of Authors", data, tmp);
        ArrayList<DataSet> ld = new ArrayList<>();
        ld.add(d);
        ChartData chartData = new ChartData(labels, ld);
        return chartData;
    }

    @GetMapping(path="/articlesByWordCategory")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody ChartData getarticlesByWordCategory() {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Integer> data = new ArrayList<>();

        //articlesByWordCategory
        Iterator<ArticlesByWordCategory> articlesByWordCategoryIterator = articlesByWordCategoryRepository.findAll().iterator();
        while (articlesByWordCategoryIterator.hasNext()) {
            ArticlesByWordCategory a = articlesByWordCategoryIterator.next();
            labels.add(a.getW_category());
            data.add(a.getCount());
        }
        ArrayList<String> tmp = new ArrayList<>();
        DataSet d = new DataSet("Articles Per WordCategory", data, tmp);
        ArrayList<DataSet> ld = new ArrayList<>();
        ld.add(d);
        ChartData chartData = new ChartData(labels, ld);
        return chartData;
    }

    @PostMapping(path = "/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody String addContent (@RequestParam String json_id) {
        ArrayList<String> list = readFile(json_id);
        json_id = "document_parses/pdf_json/" + json_id + ".json";

        //if already exists
        if (contentRepository.existsById(json_id)) {
            return "already Saved";
        }

        //author
        int i=0, numberOfAuthors=0;
        String tmp;
        while ((tmp = list.get(i))!="nextIsBodyText") {
            numberOfAuthors++;
            String name = tmp;
                System.out.println(name);
                System.out.println(i);
            String institution = list.get(i+1);
                System.out.println(institution);
                System.out.println(i);
            String settlement = list.get(i+2);
                System.out.println(settlement);
                System.out.println(i);
            String country = list.get(i+3);
            Author a = new Author();
            a.setJson_id(json_id);
            a.setName(name);
            a.setInstitution(institution);
            a.setSettlement(settlement);
            a.setCountry(country);
            authorRepository.save(a);
            i=i+4;

            //update authorsByCountry
            AuthorsByCountry authorsByCountry;
            if (!(authorsByCountryRepository.findById(a.getCountry()).isPresent())) {
                authorsByCountry = new AuthorsByCountry();
                authorsByCountry.setCountry(a.getCountry());
                authorsByCountry.setCount(1);
            }
            else {
                authorsByCountry = authorsByCountryRepository.findById(a.getCountry()).get();
                authorsByCountry.setCount(authorsByCountry.getCount()+1);
            }
            authorsByCountryRepository.save(authorsByCountry);

            //update authorsBySettlement
            AuthorsBySettlement authorsBySettlement;
            if (!(authorsBySettlementRepository.findById(a.getSettlement()).isPresent())) {
                authorsBySettlement = new AuthorsBySettlement();
                authorsBySettlement.setSettlement(a.getSettlement());
                authorsBySettlement.setCount(1);
            }
            else {
                authorsBySettlement = authorsBySettlementRepository.findById(a.getSettlement()).get();
                authorsBySettlement.setCount(authorsBySettlement.getCount()+1);
            }
            authorsBySettlementRepository.save(authorsBySettlement);
        }

        //content
        String bodyText = list.get(i+1);
        Content p = new Content();
        p.setJson_id(json_id);
        p.setBodyText(bodyText);
        contentRepository.save(p);

        //update articlesbyauthorsCategory
        AuthorsByCategory authorsByCategory;
        String category;
        if (numberOfAuthors==1) category = "=1";
        else if (numberOfAuthors==2) category = "=2";
        else category = ">=3";
        if (!(authorsByCategoryRepository.findById(category).isPresent())) {
            authorsByCategory = new AuthorsByCategory();
            authorsByCategory.setCategory(category);
            authorsByCategory.setCount(1);
        }
        else {
            authorsByCategory = authorsByCategoryRepository.findById(category).get();
            authorsByCategory.setCount(authorsByCategory.getCount()+1);
        }
        authorsByCategoryRepository.save(authorsByCategory);

        //update articlesByWordCategory
        ArticlesByWordCategory articlesByWordCategory;
        String w_category;
        int numberOfWords = Integer.parseInt(list.get(i+2));
        if (numberOfWords<=3000) w_category = "<3000";
        else w_category = ">=3000";
        if (!(articlesByWordCategoryRepository.findById(w_category).isPresent())) {
            articlesByWordCategory = new ArticlesByWordCategory();
            articlesByWordCategory.setW_category(w_category);
            articlesByWordCategory.setCount(1);
        }
        else {
            articlesByWordCategory = articlesByWordCategoryRepository.findById(w_category).get();
            articlesByWordCategory.setCount(articlesByWordCategory.getCount()+1);
        }
        articlesByWordCategoryRepository.save(articlesByWordCategory);

        return "Saved";
    }

    public ArrayList<String> readFile(String json_id) {
        String names = "";
        String countries="";
        String settlements="";
        String institutions="";
        BufferedReader reader;
        StringBuilder str = new StringBuilder("");
        Integer j=0;
        StringBuilder id_str = new StringBuilder("");
        StringBuilder title_str = new StringBuilder("");
        ArrayList<String> list = new ArrayList<String>();
        Integer numberOfAuthors = 0;
        Integer numberOfWords = 0;
        try {
            reader = new BufferedReader(new FileReader(
                "C:\\Users\\Public.Public-PC\\Desktop\\json_files\\" + json_id + ".json")); // read the file
            String line = reader.readLine(); // read first line
            line = reader.readLine(); // read second line
            // split the line into words
            String[] words = line.split("\\W+");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].replaceAll("[^\\w]", "");
            }
            id_str.append(words[2]);
            // id is ready
            line = reader.readLine();
            line = reader.readLine();
            words = line.split("\\W+");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].replaceAll("[^\\w]", "");
            }
            for (int i = 2; i < words.length; i++) {
                title_str.append(words[i]);
                title_str.append(" ");
            }
            // title is ready

            // now I will take information from authors
            // name (first, middle, last), institution, settlement, country
            do{
                line = reader.readLine();
                words = line.split("\\W+");
                for (int i = 0; i < words.length; i++) {
                    words[i] = words[i].replaceAll("[^\\w]", "");
                }
                if(words.length >= 2 && words[1].contentEquals("first")){ // name of the author
                    numberOfAuthors++;
                    String name="";
                    for (int i = 2; i < words.length; i++) {
                        name = name + words[i] + "-";
                    }
                    // for the middle name
                    String[] newwords;
                    String newline = reader.readLine();
                    if (!(newline.contains("],"))) {
                        newline = reader.readLine();
                        newwords = newline.split("\\W+");
                        for (int i = 0; i < newwords.length; i++) {
                            newwords[i] = newwords[i].replaceAll("[^\\w]", "");
                        }
                        name = name + newwords[1] + "-";
                        reader.readLine();
                    }
                    // for last name
                    newline = reader.readLine();
                    newwords = newline.split("\\W+");
                    for (int i = 0; i < newwords.length; i++) {
                        newwords[i] = newwords[i].replaceAll("[^\\w]", "");
                    }
                    name = name + newwords[2];
                    list.add(name); // put the name into the data structure
                }
                if(words.length >= 2 && words[1].contentEquals("country")){ // country_name
                    list.add(words[2]);
                    //System.out.println(words[2]);
                }
                if(words.length >= 2 && words[1].contentEquals("settlement")){ // settlement
                    list.add(words[2]);
                    //System.out.println(words[2]);
                }
                if(words.length == 1 && words[1].contentEquals("institution")){ // empty institution
                    String inst=""; // at the end it has an extra space!!!!!
                    list.add(inst);
                    //System.out.println(words[2]);
                }
                else if(words.length >= 2 && words[1].contentEquals("institution")){ // institution
                    String inst=""; // at the end it has an extra space!!!!!
                    for (int i = 2; i < words.length; i++) {
                        inst = inst + words[i] + " ";
                    }
                    list.add(inst);
                    //System.out.println(words[2]);
                }
                if(words.length > 0){
                    // when the authors finish break from the loop
                    if(words[1].contentEquals("abstract")){
                        break;
                    }
                }
            }
            while(line != null);

            // this loop is for the body text
            while (line != null) {
                words = line.split("\\W+");
                for (int i = 0; i < words.length; i++) {
                    words[i] = words[i].replaceAll("[^\\w]", "");
                }
                // find "body_text"
                if(words.length > 0 && words[1].contentEquals("body_text")){
                    line = reader.readLine();
                    line = reader.readLine();
                    words = line.split("\\W+");
                    for (int i = 0; i < words.length; i++) {
                        words[i] = words[i].replaceAll("[^\\w]", "");
                    }
                    // and append the first paragraph
                    for (int i = 2; i < words.length; i++) {
                        str.append(words[i]);
                        numberOfWords++;
                        str.append(" ");
                    }
                    // give 2 lines space to separete the paragraphs
                    str.append("\r\n");
                    str.append("\r\n");
                    line = reader.readLine();

                    // this loop is for the rest of the paragraphs
                    do{
                        words = line.split("\\W+");
                        for (int i = 0; i < words.length; i++) {
                            words[i] = words[i].replaceAll("[^\\w]", "");
                        }
                        if(words.length > 0 && words[1].contentEquals("section")){
                            // now I will put the text
                            line = reader.readLine();
                            line = reader.readLine();
                            line = reader.readLine();
                            words = line.split("\\W+");
                            for (int i = 0; i < words.length; i++) {
                                words[i] = words[i].replaceAll("[^\\w]", "");
                            }
                            for (int i = 2; i < words.length; i++) {
                                str.append(words[i]);
                                numberOfWords++;
                                str.append(" ");
                            }
                            str.append("\r\n");
                            str.append("\r\n");
                        }
                        // when body text finish break the loop
                        if(words.length > 0 && words[1].contentEquals("bib_entries")){
                            j = 1;
                            //System.out.println("\r\n 1st break \r\n");
                            break;
                        }
                        line = reader.readLine();
                    }
                    while(j == 0);
                }
                if(j == 1){
                    //System.out.println("\r\n 2nd break \r\n");
                    break;
                }
                line = reader.readLine();
            }
            System.out.println(str); // body_text
            System.out.println("Title: " + title_str); // title text
            System.out.println("id: " + id_str); // paper id
            // I will print the names of the authors
            Iterator itr=list.iterator();
            while(itr.hasNext()){
                //System.out.println(itr.next());
                names = names + itr.next() + " ";
            }
            list.add("nextIsBodyText");
            list.add(str.toString());
            list.add(numberOfWords.toString());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
