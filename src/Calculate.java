
package chess.danger;
import java.util.HashMap;

public class Calculate {
    
    //dosya dizisinin satirlari için
    private int index=0;
    
    //Dosyadan okunan degerler ve puanlar icin 
    private String[][] dosya= new String[8][8] ;
    private Double[][] puanlar=new Double[8][8];
    //Tehtit degerleri icin
    private Double siyah_skor;
    private Double beyaz_skor;
    //Siyah ve beyaz taslarin, her bir oyuncusu icin sayi degerleri
    private int kale_siyah;
    private int at_siyah;
    private int fil_siyah;
    private int piyon_siyah;
    
    private int kale_beyaz;
    private int at_beyaz;
    private int fil_beyaz;
    private int piyon_beyaz;
    
    //Siyah ve beyaz taslarin hangi tasin hangi konumda oldugunu tutacak HashMap'ler
    private HashMap <String, String> siyahlar = new HashMap<>();
    private HashMap <String, String> beyazlar = new HashMap<>();
    
    //Constructor ile tum tas sayilarina ve puan degerlerine 0 atanir
    public Calculate(){
        kale_siyah=0;
        at_siyah=0;
        fil_siyah=0;
        piyon_siyah=0;

        kale_beyaz=0;
        at_beyaz=0;
        fil_beyaz=0;
        piyon_beyaz=0;
        
        siyah_skor=0.;
        beyaz_skor=0.;
  
    }
    
    //Dosyadan okunan her satir icin dosya ve puanlar dizilerine eleman atilir
    public void Baslangic_Durum(String line){
      //Okunan her satir bosluklara gore ayrilir
      String[] parts = line.split("\\s+");
      //parts degiskenindeki her bir eleman icin 
      for(int j=0;j<8;j++){
            //dosya dizisinin sirayla her satirina, okunan degerler atilir
            dosya[index][j]=parts[j];
            //puanlar dizisinin her satirina, okunan elemanin ilk karakterine gore
            //Skor_Tablosu metodundan tehtit degeri atilir
            puanlar[index][j]=Skor_Tablosu(dosya[index][j].charAt(0));
            //Taslarin konum ve cinsini belirlemek icin Oyuncu metodu cagirilir.
            Oyuncular(dosya[index][j], index, j);        
        }
        //Dosyanin bir sonraki satirini belirtmek icin bir arttirilir
        index++; 
    
    }
    
    //Siyah puan
    public Double Siyah_Skor_Getir(){ 
        //Siyah puan dondurulur
        return siyah_skor;
    }
    
    //Beyaz puan
    public Double Beyaz_Skor_Getir(){
        //Beyaz puan dondurulur
        return beyaz_skor;
    }
    
    //Tehtit altinda olan tum taslar ve tehtit degerleri hesaplanir 
    public void Run(){
        //Siyah taslardan tehtit altinda olanlar hesaplanir
        Siyah_Tehtit_Hesapla();
        //Beyaz taslardan tehtit altinda olanlar hesaplanir
        Beyaz_Tehtit_Hesapla();
        //Siyah ve beyaz taslarin puanlari hesaplanir 
        siyah_skor=Siyah_Skor();
        beyaz_skor=Beyaz_Skor();
          
    }
    
    //Satranc tahtasindaki tum siyah ve beyaz oyuncular ve konumları bulunur
    private void Oyuncular(String oyuncu,int konum1,int konum2){
    //Parametre olarak alinan oyuncunun ikinci karakteri siyahsa
    if(oyuncu.charAt(1)=='s'){
      //Oyuncu_Adeti_Siyah metodu ile, oyuncunun sayisi bulunur
      //Ornegin ilk 'ks' degeri icin 1 doner
      int adet_siyah=Oyuncu_Adeti_Siyah(oyuncu.charAt(0));
      //siyah oyuncular icin kacinci oyuncu oldugu ve konum degeri eklenir.
      siyahlar.put(oyuncu+Integer.toString(adet_siyah),Integer.toString(konum1)+Integer.toString(konum2));
    }
    //Parametre olarak alinan oyuncunun ikinci karakteri beyazsa
    if(oyuncu.charAt(1)=='b'){
       //Oyuncu_Adeti_Beyaz metodu ile, oyuncunun sayisi bulunur
       //Ornegin ilk 'pb' degeri icin 1 doner. Besinci 'pb' icin 5 doner.
      int adet_beyaz=Oyuncu_Adeti_Beyaz(oyuncu.charAt(0));
      //beyaz oyuncular icin kacinci oyuncu oldugu ve konum degeri eklenir.
      beyazlar.put(oyuncu+Integer.toString(adet_beyaz),Integer.toString(konum1)+Integer.toString(konum2));
      
    }
    
    }
    
    //Siyah taslardan tehtit altinda olanlar bulunur
    private void Siyah_Tehtit_Hesapla(){
 
        //Siyah taslarin tehtit altinda olanlarinin bulunmasi icin 
        //beyaz taslara bakilir
        //Beyazlar HashMap 'indeki key degerlerine bakilir
        for(String i : beyazlar.keySet()){
            //Eger key degerindeki ilk karakter 'a' ise
            //at tehtit
            if(i.charAt(0)=='a'){
            //Kosula uyan key icin, value degerleri alinir
            //Value degerinin ilk karakteri x konumunu
            //ikinci karakteri y konumunu gosterir
            int konumX = (beyazlar.get(i).charAt(0))-'0';
            int konumY = (beyazlar.get(i).charAt(1))-'0';
            //Atin tehtit ettigi taslari bulmak icin hedef oyuncu rengiyle birlikte konum verilir
            At_Tehtit(konumX,konumY,'s');

            }
            //Eger key degerindeki ilk karakter 'f' ise
            //fil tehtit
            if(i.charAt(0)=='f'){
            //Kosula uyan key icin, value degerleri alinir
            //Value degerinin ilk karakteri x konumunu
            //ikinci karakteri y konumunu gosterir
            int konumX = (beyazlar.get(i).charAt(0))-'0';
            int konumY = (beyazlar.get(i).charAt(1))-'0';
            //Filin tehtit ettigi taslari bulmak icin hedef oyuncu rengiyle birlikte konum verilir
            Fil_Tehtit(konumX, konumY,'s','b');

            }

        }
    
    }
    
    //Beyaz taslardan tehtit altinda olanlar bulunur
    private void Beyaz_Tehtit_Hesapla(){
        
        //Beyaz taslarin tehtit altinda olanlarinin bulunmasi icin 
        //siyah taslara bakilir
        //Siyahlar HashMap 'indeki key degerlerine bakilir
        for(String i : siyahlar.keySet()){
            //Eger key degerindeki ilk karakter 'a' ise
            //at tehtit
            if(i.charAt(0)=='a'){
            //Kosula uyan key icin, value degerleri alinir
            //Value degerinin ilk karakteri x konumunu
            //ikinci karakteri y konumunu gosterir
            int konumX = siyahlar.get(i).charAt(0)-'0';
            int konumY = siyahlar.get(i).charAt(1)-'0';
            //Atin tehtit ettigi taslari bulmak icin hedef oyuncu rengiyle birlikte konum verilir
            At_Tehtit(konumX,konumY,'b');

            }
            //Eger key degerindeki ilk karakter 'f' ise
            //fil tehtit
            if(i.charAt(0)=='f'){
            //Kosula uyan key icin, value degerleri alinir
            //Value degerinin ilk karakteri x konumunu
            //ikinci karakteri y konumunu gosterir 
            int konumX = siyahlar.get(i).charAt(0)-'0';
            int konumY = siyahlar.get(i).charAt(1)-'0';
            //Filin tehtit ettigi taslari bulmak icin hedef oyuncu rengiyle birlikte konum verilir
            Fil_Tehtit(konumX, konumY,'b','s');

            }

        }
    }
    
    //Siyah taslarin tehtit puanlari hesaplanir
    private Double Siyah_Skor(){
        //x ve y koordinatlari icin degisken tanimlanir
        int x;
        int y;
        //Siyahlar HashMap'indeki value degerleri
        for(String i : siyahlar.values()){
           //Value'daki herbir karakter int tipinde cevrilerek
           //x ve y degiskeninde atilir
           x= i.charAt(0)-'0';
           y= i.charAt(1)-'0';
           //puanlar dizisinden belirlenen konumdaki degerler toplanarak
           //siyah_skor degiskenine atilir
           siyah_skor += puanlar[x][y];
        
        }
      //Bulunan deger dondurulur
      return siyah_skor;
    
    }
    
    //Beyaz taslarin tehtit puanlari hesaplanir
    private Double Beyaz_Skor(){
       //x ve y koordinatlari icin degisken tanimlanir
       int x;
       int y;
       //Beyazlar HashMap'indeki value degerleri
       for(String i : beyazlar.values()){
           //Value'daki herbir karakter int tipinde cevrilerek
           //x ve y degiskeninde atilir
           x= i.charAt(0)-'0';
           y= i.charAt(1)-'0';
           //puanlar dizisinden belirlenen konumdaki degerler toplanarak
           //beyaz_skor degiskenine atilir
           beyaz_skor += puanlar[x][y];
        
        }
       //Bulunan deger dondurulur
       return beyaz_skor;
    }
    
    //Atin tehtit edebilecegi taslarin bulunmasi
    private void At_Tehtit(int konumx,int konumy,char oyuncu_renk){
       //Atin tehtit edebilecegi konumlarin hesaplanacagi koordinatlar
       //Ilk satir x , ikinci satir y degerlerini tutar
       //Ornegin: (4,6) konumunda bulunan bir at icin
       //tehtit edebilecegi tas (2,5) konumundadir
       int [][] at_koordinatlar = { {-2,2,-2,2,-1,1,-1,1 }, {-1,1,1,-1,-2,2,2,-2} };
        
        //Dizideki tum koordinatlar icin
        for(int i=0;i<8;i++){ 
          //Parametre olarak alinan konumx ve konumy degerlerine, herbir sutundaki degerler eklenir
          int temp_x=konumx+at_koordinatlar[0][i];        
          int temp_y=konumy+at_koordinatlar[1][i];
          //Sinir degerlerin gecilip gecilmedigi kontrol edilir
          if(temp_x>=0 & temp_x<8 & temp_y>=0 & temp_y<8){
              //Eger kosul saglanirsa
              //Dosya dizisinden, o konumdaki oyuncu getirilir
              String oyuncu = dosya[temp_x][temp_y];
              //Parametre olarak verilen oyuncu rengi saglaniyorsa
              if(oyuncu.charAt(1)==oyuncu_renk){
                  //Tasin puan tablosundaki degerinin yarisi, puanlar dizisindeki bulunan konuma atilir
                  puanlar[temp_x][temp_y]=(Skor_Tablosu(oyuncu.charAt(0)))/2;
           
                } 

          }
            
        }   
        
    
    }
    
    //Filin tehtit edebilecegi taslarin bulunmasi
    private void Fil_Tehtit(int konumx,int konumy,char tehtit,char kendi_oyuncu){
        //Filin tehtit edebilecegi konumlarin hesaplanacagi koordinatlar
        int [][] fil_koordinat = { {-1,-1,1,1 }, {-1,1,1,-1} };
            //Dizideki tum koordinatlar icin 
            for(int i=0;i<4;i++){
                //Parametre olarak alinan konumx ve konumy degerlerine, herbir sutundaki degerler eklenir
                int tempx=konumx+fil_koordinat[0][i];
                int tempy=konumy+fil_koordinat[1][i];
                //Sinir degerlerini gecene kadar, fil_koordinat dizisindeki sutun degerleri
                //yeniden hesaplanir
                while( tempx>=0&tempx<8 & tempy>=0&tempy<8){
                    //Eger kosul saglanirsa
                    //Dosya dizisinden, o konumdaki oyuncu getirilir 
                    String temp_oyuncu = dosya[tempx][tempy];
                    //Parametre olarak verilen tehtit rengi saglaniyorsa
                    if(temp_oyuncu.charAt(1)==tehtit){
                        //Tasin puan tablosundaki degerinin yarisi, puanlar dizisindeki bulunan konuma atilir
                        puanlar[tempx][tempy]=(Skor_Tablosu(temp_oyuncu.charAt(0)))/2;
                        //Bir sonraki fil_koordinat sutuna gecilir
                        break;
                    }
                    //Parametre olarak verilen tasin kendi rengi saglaniyorsa
                    else if(temp_oyuncu.charAt(1)==kendi_oyuncu){
                         //Bir sonraki fil_koordinat sutuna gecilir
                        break;
                    }
                    
                    //Ayni koordinat icin yeni x ve y degerleri bulunur
                    tempx=tempx+fil_koordinat[0][i];
                    tempy=tempy+fil_koordinat[1][i];
                        
                    }
               
                }       
    }
    
    //Puanlama da kullanilan degerler icin
    private Double Skor_Tablosu(char chr){
        //Parametre olarak alinan karakter icin puan degeri dondurulur
     switch (chr) {

            case 'p':
                return 1.;
            case 'a':
                return 3.;
            case 'f':
                return 3.;
            case 'k':
                return 5.;
            case 'v':
                return 9.;
            case 's':
                return 100.;
            default:
                return 0.; 
    
    }
      
    
    }
    
    //Siyah taslarin turune gore sayilarinin bulunmasi
    private int Oyuncu_Adeti_Siyah(char chr){
        //Parametre olarak alinan karaktere karsilik gelen degiskenin degeri arttirilir   
        switch (chr) {
            case 'p':
                return piyon_siyah++;
            case 'a':
                return at_siyah++;
            case 'f':
                return fil_siyah++;
            case 'k':
                return kale_siyah++;
            default:
                return 1; 
    
            }
    }
    
    //Beyaz taslarin turune gore sayilarinin bulunmasi
    private int Oyuncu_Adeti_Beyaz(char chr){
        //Parametre olarak alinan karaktere karsilik gelen degiskenin degeri arttirilir    
        switch (chr) {

            case 'p':
                return piyon_beyaz++;
            case 'a':
                return at_beyaz++;
            case 'f':
                return fil_beyaz++;
            case 'k':
                return kale_beyaz++;
            default:
                return 1; 
    
            }
    }
    
}
