import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		String[] genres = {"classic", "pop", "classic", "classic", "pop"};
		int[] plays = {500, 600, 150, 800, 2500};
		System.out.println(Arrays.toString(new Solution().solution(genres, plays)));
	}

}
class Solution {
    public int[] solution(String[] genres, int[] plays) { 
    	ArrayList<Integer> answer = new ArrayList<>();
        // 장르 마다의 합계
        // 장르 마다의 1,2위
        int n = genres.length;
        HashMap<String, GenreAlbums> map = new HashMap<String, GenreAlbums>();
        for (int i = 0; i < n; i++) {
			if (map.containsKey(genres[i])) {
        		GenreAlbums genreAlbums = map.get(genres[i]);
        		genreAlbums.addAlbum(new Album(i, plays[i]));
        		map.put(genres[i], genreAlbums);
			}
        	else {
        		GenreAlbums genreAlbums = new GenreAlbums(genres[i]);
        		genreAlbums.addAlbum(new Album(i, plays[i]));
        		map.put(genres[i], genreAlbums);
        	}
        }
        ArrayList<GenreAlbums> list = new ArrayList<>(map.values());
        Collections.sort(list);
        for (GenreAlbums genreAlbums : list) {
        	int[] result = genreAlbums.getBestAlbumIDs();
        	for (int idx : result)
        		answer.add(idx);
        }
        
        return answer.stream().mapToInt(i -> i).toArray();
    }
    
    class GenreAlbums implements Comparable<GenreAlbums>{
    	String genre;
    	int plays;
    	ArrayList<Album> albums;
    	
    	public GenreAlbums(String genre) {
    		this.genre = genre;
    		plays = 0;
    		albums = new ArrayList<>();
    	}
    	public String getGenre() { return this.genre; }
    	public void addAlbum(Album album) {
    		this.plays += album.play;
    		this.albums.add(album);
    	}
    	
    	public int[] getBestAlbumIDs() {
    		int[] result;
    		Collections.sort(albums);
    		if (albums.size() == 1) {
    			result = new int[1];
    			result[0] = albums.get(0).id;
    		} else {
    			result = new int[2];
       			result[0] = albums.get(0).id;
       			result[1] = albums.get(1).id;
    		}
    		return result;
     	}
    	
		@Override
		public int compareTo(GenreAlbums genreAlbums) {
			return genreAlbums.plays - this.plays; 
		}
		@Override
		public String toString() {
			return this.genre + " plays: " + this.plays;
		}
    	
    }
    
    class Album implements Comparable<Album>{
    	int id;
    	int play;
    	
    	public Album(int id, int play) {
    		this.id = id;
    		this.play = play;
    	}
    	
		@Override
		public int compareTo(Album a) {
			return a.play - this.play;
		}
    }
}