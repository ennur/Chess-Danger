
package chess.danger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Run {
    
    //Dosya yolu tutulur
    private String secilen_dosya ;
    //Calculate sinifi nesnesi
    private Calculate cal;
    
    //Dosya secimi ve hesaplamalar icin constructor
    public Run(){
        //Calculate sinifindan yeni bir nesne olusturulur
        cal = new Calculate();
        //Dosya_Sec metodu ile,secim paneli cikartilir
        //Secilen dosyanin yolu secilen_dosya degiskenine atilir
        secilen_dosya=Dosya_Sec();
        //Dosya yolu,okunmak icin Dosya_Oku metoduna gonderilir
        Dosya_Oku(secilen_dosya);
        //Tehtit hesaplamalari icin calculate sinifinin Run metodu cagirilir
        cal.Run();
 
    }
    
    //Calculate sinifindan siyah tehtit puanini almak icin
    public Double Siyah(){
      return cal.Siyah_Skor_Getir();
    }
    
    //Calculate sinifindan beyaz tehtit puanini almak icin
    public Double Beyaz(){
      return cal.Beyaz_Skor_Getir();
    }
    
    //Dosya secilir
    private String Dosya_Sec(){
        //Home klasorunu gosteren bir JFileChooser nesnesi olusturulur
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        
        //Sadece txt dosyalarını secebilmek icin filtre olusturulur
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt dosyaları", "txt");
        jfc.addChoosableFileFilter(filter);
        
        //Bir "Dosya Ac" dosya secici iletisim kutusu acilir
        int returnValue = jfc.showOpenDialog(null);
        
        //Dosya yolu icin
        String selectedFile = "";
        
        //Return degeri kontrol edilir 
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            //Eger onaylanmissa (dosya secilmisse) yolu selectedFile degiskenine atar
            selectedFile =jfc.getSelectedFile().getPath();
            
        }
        //selectedFile dondurulur
        return selectedFile;
    
    }
    
    //Secilen dosya okunur
    private void Dosya_Oku(String secilen_dosya){
        //Dosyanin okunmasi
        try (FileReader file = new FileReader(secilen_dosya)) {
            try (BufferedReader reader = new BufferedReader(file)) {

                //Dosyadan okuncak satirlari tutan degisken 
                String line = "";            
                //Satir sonuna gelene kadar okuma yapilir
                while ((line = reader.readLine()) != null) {
                        //Okunan her satir Baslangic_Durum metoduna parametre olarak verilir
                        cal.Baslangic_Durum(line);      
                   }       
                  
            }         
        }
        //Dosyanin acilmasi sirasinda herhangi bir hata olursa bunu ekrana yazar
        catch (IOException e) {
            System.out.print(e.toString());
        }
    
    }
    
    
}
