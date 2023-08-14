package com.example.baitaplonandroid;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.baitaplonandroid.model.Category;
import com.example.baitaplonandroid.model.Question;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    //Tên database
    private static final String DATABASE_NAME = "Question.db";

    //Vertion
    private static final int VERTION = 1;

    private SQLiteDatabase db;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        this.db = sqLiteDatabase;

        //Biến bảng chuyên mục
        final String CATEGORIES_TABLE = "CREATE TABLE " +
                Table.CategoriesTable.TABLE_NAME + "( " +
                Table.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        //Biến bảng question
        final String QUESTIONS_TABLE = "CREATE TABLE " +
                Table.QuestionsTable.TABLE_NAME + " ( " +
                Table.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                Table.QuestionsTable.COLUMN_ANSWER + " INTEGER, " +
                Table.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + Table.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        //Tạo bảng
        db.execSQL(CATEGORIES_TABLE);
        db.execSQL(QUESTIONS_TABLE);

        //insert dữ liệu
        CreateCategories();
        CreateQuestions();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + Table.CategoriesTable.COLUMN_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Table.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    //insert chủ đề vào csdl
    private void insertCategories (Category category) {
        ContentValues values = new ContentValues();
        values.put(Table.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(Table.CategoriesTable.TABLE_NAME, null, values);
    }

    //Các giá trị insert
    private void CreateCategories(){
        //Môn văn id = 1
        Category c1 = new Category("Ngữ văn");
        insertCategories(c1);

        //Môn lịch sử id = 2
        Category c2 = new Category("Lịch sử");
        insertCategories(c2);

        //Môn địa lý id = 3
        Category c3 = new Category("Địa lý");
        insertCategories(c3);
    }

    //insert câu hỏi và đáp án vào csdl
    private  void insertQuestion(Question question){
        ContentValues values = new ContentValues();

        values.put(Table.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        values.put(Table.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        values.put(Table.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        values.put(Table.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        values.put(Table.QuestionsTable.COLUMN_OPTION4, question.getOption4());
        values.put(Table.QuestionsTable.COLUMN_ANSWER, question.getAnswer());
        values.put(Table.QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());

        db.insert(Table.QuestionsTable.TABLE_NAME, null, values);
    }

    //Tạo dữ liệu bảng câu hỏi
    private void CreateQuestions(){

        /*Dữ liệu bảng question gồm:
        + Câu hỏi
        + 4 đáp án
        + Vị trí đáp án đúng: 1 - A, 2 - B, 3 - C, 4 - D
        + id của chủ đề
         */

        //Câu hỏi Văn
        Question q1 = new Question("Tôi đi học” của Thanh Tịnh được viết theo thể loại nào?",
                "A. Bút kí.",
                "B. Truyện ngắn trữ tình.",
                "C. Tiểu thuyết.",
                "D. Tuỳ bút",
                2, 1);
        insertQuestion(q1);

        Question q2 = new Question(" Dòng nào chứa từ ngữ không phù hợp trong mỗi nhóm từ ngữ sau đây?",
                "A. Xe cộ: xe đạp, xe máy, ô tô, xe chỉ, xích lô, tàu điện.",
                "B. Đồ dùng học tập: bút chì, thước kẻ, sách giáo khoa, vở.",
                "C. Cây cối: cây tre, cây chuối, cây cau, cây gạo, cây bàng, cây cọ.",
                "D. Nghệ thuật: âm nhạc, vũ đạo, văn học, điện ảnh, hội họa.",
                1, 1);
        insertQuestion(q2);

        Question q3 = new Question("Nhà văn Nguyên Hồng tên thật là gì?",
                "A. Nguyễn Nguyên Hồng.",
                "B. Nguyễn Hồng.",
                "C. Hồng Nguyên.",
                "D. Nguyên Hồng.",
                1, 1);
        insertQuestion(q3);

        Question q4 = new Question("Đối với Giôn-xi (Trong tác phẩm Chiếc lá cuối cùng), chiếc lá cuối cùng rụng hay không rụng có ý nghĩa như thế nào?",
                "A. Nếu chiếc lá ấy rụng thì cô sẽ không tiếp tục vẽ nữa.",
                "B. Nếu chiếc lá ấy rụng thì cô sẽ rất đau khổ.",
                "C. Cô không còn muốn quan tâm đến chiếc lá cuối cùng nữa.",
                "D. Chiếc lá rụng hay không sẽ quyết định số phận của cô.",
                4, 1);
        insertQuestion(q4);

        Question q5 = new Question("Trong tác phẩm Cô bé bán diêm, em bé quẹt que diêm thứ nhất," +
                " em bé tưởng chừng ngồi trước một lò sưởi. Ý nào nói đúng về mộng tưởng đó?",
                "A. Em mơ về một mái ấm gia đình.",
                "B. Em nhớ tới ngọn lửa mà bà nhen nhóm năm xưa.",
                "C. Đang trải qua lạnh giá rét mướt, em mơ được sưởi ấm.",
                "D. Em mơ ngọn lửa và hơi ấm lò sưởi xua tan cảnh tăm tối, lạnh lẽo của đời mình.",
                4, 1);
        insertQuestion(q5);

        Question q6 = new Question("Trong tác phẩm Tức nước vỡ bờ, câu trả lời của chị Dậu khi nghe anh Dậu khuyên can:" +
                " “Thà ngồi tù. Để cho chúng nó làm tình làm tội mãi thế, tôi không chịu được” nói lên thái độ gì của chị?",
                "A. Thái độ không chịu khuất phục.",
                "B. Thái độ bất cần.",
                "C. Thái độ kiêu căng.",
                "D. Cả A, B, C đều đúng.",
                1, 1);
        insertQuestion(q6);

        Question q7 = new Question("Thế nào là từ ngữ địa phương?",
                "A. Là từ ngữ toàn dân đều biết và hiểu",
                "B. Là từ ngữ chỉ được dùng duy nhất ở một địa phương.",
                "C. Là từ ngữ chỉ được dùng ở một (một số) địa phương nhất định.",
                "D. Là từ ngữ được ít người biết đến.",
                3, 1);
        insertQuestion(q7);

        Question q8 = new Question("Khi nào không nên nói giảm nói tránh?",
                "A. Khi cần phải nói năng lịch sự, có văn hóa.",
                "B. Khi muốn bày tỏ tình cảm của mình.",
                "C. Khi cần phải nói thẳng, nói đúng sự thật.",
                "D. Khi muốn làm cho người nghe bị thuyết phục.",
                3, 1);
        insertQuestion(q8);

        Question q9 = new Question("Bài thơ “Tức cảnh Pác Bó” do ai sáng tác?",
                "A. Tố Hữu.",
                "B. Chế Lan Viên.",
                "C. Phan Bội Châu.",
                "D. Hồ Chí Minh.",
                4, 1);
        insertQuestion(q9);

        Question q10 = new Question("Nguyễn Ái Quốc là tên gọi của Chủ tích Hồ Chí Minh thời kì nào?",
                "A. Thời kì niên thiếu Bác sống ở Huế.",
                "B. Thời kì Bác hoạt động cách mạng ở nước ngoài.",
                "C. Thời kì Bác lãnh đạo nhân dân ta kháng chiến chống Pháp.",
                "D. Thời kì Bác lãnh đạo nhân dân ta kháng chiến chống Mĩ.",
                3, 1);
        insertQuestion(q10);


        //Câu hỏi Sử
        Question q11 = new Question("Cuộc khai thác thuộc địa lần thứ hai (1919-1929) của thực dân Pháp ở Đông Dương được diễn ra trong hoàn cảnh nào?",
                "A. Nước Pháp đang chuyển sang giai đoạn chủ nghĩa đế quốc",
                "B. Nước Pháp bị thiệt hại nặng nề do cuộc chiến tranh xâm lược Việt Nam",
                "C. Nước Pháp bị thiệt hại nặng nề do cuộc chiến tranh thế giới thứ nhất (1914-1918)",
                "D. Tình hình kinh tế, chính trị ở Pháp ổn định",
                3, 2);
        insertQuestion(q11);

        Question q12 = new Question("Thực dân Pháp tiến hành cuộc khai thác thuộc địa lần thứ hai ở Đông Dương (1919 - 1929) khi",
                "A. Hệ thống thuộc địa của chủ nghĩa đế quốc tan rã.",
                "B. Thế giới tư bản đang lâm vào khủng hoảng thừa.",
                "C. Cuộc chiến tranh thế giới thứ nhất kết thúc.",
                "D. Kinh tế các nước tư bản đang trên đà phát triển.",
                3, 2);
        insertQuestion(q12);

        Question q13 = new Question("Ngành kinh tế nào được thực dân Pháp đầu tư nhiều nhất trong cuộc khai thác thuộc địa lần thứ hai (1919 – 1929) ở Đông Dương?",
                "A. Nông nghiệp",
                "B. Công nghiệp",
                "C. Tài chính- ngân hàng",
                "D. Giao thông vận tải",
                1, 2);
        insertQuestion(q13);

        Question q14 = new Question("Trong cuộc khai thác thuộc địa lần thứ hai ở Đông Dương (1919 - 1929), thực dân Pháp tập trung đầu tư vào",
                "A. Đồn điền cao su.",
                "B. Công nghiệp hóa chất.",
                "C. Công nghiệp luyện kim. ",
                "D. Ngành chế tạo máy.",
                1, 2);
        insertQuestion(q14);

        Question q15 = new Question("Trong cuộc khai thác thuộc địa lần thứ hai ở Đông Dương (1919 - 1929), thực dân Pháp chú trọng đầu tư vào",
                "A. Chế tạo máy.",
                "B. Công nghiệp luyện kim.",
                "C. Công nghiệp hóa chất.",
                "D. Khai thác mỏ.",
                4, 2);
        insertQuestion(q15);

        Question q16 = new Question("Trong cuộc khai thác thuộc địa lần thứ hai (1919), thực dân Pháp sử dụng biện pháp nào để tăng ngân sách Đông Dương?",
                "A. Mở rộng quy mô sản xuất",
                "B. Khuyến khích phát triển công nghiệp nhẹ",
                "C. Tăng thuế và cho vay lãi",
                "D. Mở rộng trao đổi buôn bán",
                3, 2);
        insertQuestion(q16);

        Question q17 = new Question("Giai cấp nào trong xã hội Việt Nam đầu thế kỉ XX có quan hệ gắn bó với giai cấp nông dân?",
                "A. Công nhân",
                "B. Địa chủ",
                "C. Tư sản",
                "D. Tiểu tư sản",
                1, 2);
        insertQuestion(q17);

        Question q18 = new Question("Sau chiến tranh thế giới thứ nhất, lực lượng xã hội có khả năng vươn lên nắm ngọn cờ lãnh đạo cách mạng Việt Nam là",
                "A. Đại địa chủ",
                "B. Trung địa chủ",
                "C. Tiểu địa chủ",
                "D. Trung, tiểu địa chủ",
                4, 2);
        insertQuestion(q18);

        Question q19 = new Question("Trung và tiểu địa chủ Việt Nam sau Chiến tranh thế giới thứ nhất là lực lượng",
                "A. có tinh thần chống Pháp và tay sai. ",
                "B. làm tay sai cho Pháp.",
                "C. bóc lột nông dân và làm tay sai cho Pháp.",
                 "D. thỏa hiệp với Pháp.",
                1, 2);
        insertQuestion(q19);

        Question q20 = new Question("Trước khi thực dân Pháp nổ súng xâm lược vào năm 1858, Việt Nam vẫn là: ",
                "A. một quốc gia độc lập, có chủ quyền. ",
                "B. làm tay sai cho Pháp.",
                "C. bóc lột nông dân và làm tay sai cho Pháp.",
                "D. thỏa hiệp với Pháp.",
                1, 2);
        insertQuestion(q20);


        //Câu hỏi Địa
        Question q21 = new Question(" Châu Á có diện tích đứng thứ mấy trong các châu lục trên thế giới?",
                "A. 1.",
                "B. 2.",
                "C. 3.",
                "D. 4.",
                1, 3);
        insertQuestion(q21);

        Question q22 = new Question("Dãy núi nào sau đây là dãy núi cao và đồ sộ nhất châu Á?",
                "A. Hi-ma-lay-a.",
                "B. Côn Luân.",
                "C. Thiên Sơn.",
                "D. Cap-ca.",
                1, 3);
        insertQuestion(q22);

        Question q23 = new Question("Lãnh thổ Việt Nam gồm bộ phận:",
                "A. Phần đất liền.",
                "B. Các đảo và vùng biển.",
                "C. Vùng trời.",
                "D. Cả 3 ý A,B,C.",
                4, 3);
        insertQuestion(q23);

        Question q24 = new Question("Quốc gia đông dân nhất châu Á là",
                "A. Trung Quốc.",
                "B. Thái Lan.",
                "C. Việt Nam.",
                "D. Ấn Độ.",
                1, 3);
        insertQuestion(q24);

        Question q25 = new Question("Nội lực là gì?",
                "A. Là lực sinh ra bên trong lòng Trái Đất.",
                "B. Là lực sinh ra bên ngoài Trái Đất.",
                "C. Là lực phát sinh từ lớp vỏ Trái Đất.",
                "D.  Là lực phát sinh từ phát sinh Mặt Trời.",
                1, 3);
        insertQuestion(q25);

        Question q26 = new Question("Đặc điểm dân cư, xã hội châu Á là:",
                "A. Một châu lục đông dân nhất thế giới.",
                "B. Dân cư thuộc nhiều chủng tộc.",
                "C. Nơi ra đời của các tôn giáo lớn.",
                "D. Tất cả các ý trên.",
                4, 3);
        insertQuestion(q26);

        Question q27 = new Question("Việt Nam không chung đường biên giới trên đất liền với nước:",
                "A. Trung Quốc.",
                "B. Lào.",
                "C. Cam-pu-chia.",
                "D. Thái Lan.",
                4, 3);
        insertQuestion(q27);

        Question q28 = new Question("Các quốc gia có lịch sử phát triển lâu đời ở châu Á là",
                "A. Trung Quốc, Ấn Độ.",
                "B. Ả-rập Xê-út, Hàn Quốc.",
                "C. Ấn Độ, Nhật Bản.",
                "D. Hàn Quốc, Nhật Bản.",
                1, 3);
        insertQuestion(q28);

        Question q29 = new Question("Đến năm 2017, kinh tế Nhật bản đứng thứ mấy trên thế giới?",
                "A. 1.",
                "B. 2.",
                "C. 3.",
                "D. 4.",
                2, 3);
        insertQuestion(q29);

        Question q30 = new Question("Để học tốt môn Địa lí Việt Nam, các em cần làm gì:",
                "A. Học thuộc tất cả các kiến thức trong SGK.",
                "B. Làm tất cả các bài tập trong SGK và sách bài tập.",
                "C. Học thuộc tất cả các kiến thức và làm tất cả các bài tập trong SGK và sách bài tập.",
                "D. Ngoài học và làm tốt các bài tập trong sách cần sưu tầm các tài liệu, khảo sát thực tế, du lịch,…",
                4, 3);
        insertQuestion(q30);

    }

    //Hàm trả về dữ liệu các chủ đề
    @SuppressLint("Range")
    public List<Category> getDataCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Table.CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(Table.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(Table.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            }
            while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }


    //Lấy dữ liệu câu hỏi và đáp án có id = id_category theo chủ đề đã chọn
    @SuppressLint("Range")
    public ArrayList<Question> getQuestions(int categoryID){
        ArrayList<Question> questionArrayList = new ArrayList<>();

        db = getReadableDatabase();

        String selection = Table.QuestionsTable.COLUMN_CATEGORY_ID + " = ? ";

        String[] selectionArgs = new String[]{String.valueOf(categoryID)};

        Cursor c = db.query(Table.QuestionsTable.TABLE_NAME,
                null, selection, selectionArgs, null, null, null);

        if (c.moveToFirst()){
            do {
                Question question = new Question();

                question.setId(c.getInt(c.getColumnIndex(Table.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION4)));
                question.setAnswer(c.getInt(c.getColumnIndex(Table.QuestionsTable.COLUMN_ANSWER)));
                question.setCategoryID(c.getInt(c.getColumnIndex(Table.QuestionsTable.COLUMN_CATEGORY_ID)));

                questionArrayList.add(question);
            }
            while (c.moveToNext());
        }
        c.close();
        return questionArrayList;
    }

    //xóa toàn bộ bảng
    public void deleteQuestionsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("question",null,null);
    }


    //tạo bảng để lưu danh sách người chơi
    /*
    public class DatabaseHelper extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "player_scores.db";

    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các trường trong bảng
    private static final String TABLE_NAME = "player_scores";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_TIME = "time";

    // Câu lệnh tạo bảng
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_SCORE + " INTEGER,"
            + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    // Phương thức khởi tạo
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Phương thức thêm người chơi mới vào cơ sở dữ liệu
    public void addPlayer(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Phương thức lấy danh sách người chơi và thời gian chơi từ cơ sở dữ liệu
    public List<String> getPlayerScores() {
        List<String> scores = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_NAME + ", " + COLUMN_TIME + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                scores.add(name + " - " + time);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return scores;
    }
    }
    */
}
