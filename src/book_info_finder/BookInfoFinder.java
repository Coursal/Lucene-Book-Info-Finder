package book_info_finder;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

//5
public class BookInfoFinder
{
    public void print_tokens_and_offsets(String label, int start_offset, int end_offset, TokenStream token_stream, OffsetAttribute offset_attribute, CharTermAttribute char_term_attribute) throws IOException
    {
        int info_start_offset = start_offset;
        int info_end_offset = end_offset;
        int previous_end_offset = end_offset;

        System.out.print(label + ": ");
        
        while(token_stream.incrementToken() && (info_start_offset - previous_end_offset) < 4)
        {
            info_start_offset = offset_attribute.startOffset();
            info_end_offset = offset_attribute.endOffset();

            if((info_start_offset - previous_end_offset) < 4)
            {
                String title_term = char_term_attribute.toString();
                System.out.print(title_term + " ");

                previous_end_offset = info_end_offset;
            }  
        }

        info_end_offset = previous_end_offset;

        System.out.print("\nOffsets: " + start_offset + " to " + info_end_offset + "\n\n");
    }
    
    
    public void find_book_info(String book_txt) throws IOException, Exception 
    {
        byte[] encoded = Files.readAllBytes(Paths.get(book_txt));
        String text = new String(encoded, "UTF-8");
        
        WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer();
        
        TokenStream token_stream = analyzer.tokenStream("contents", new StringReader(text));
        OffsetAttribute offset_attribute = token_stream.addAttribute(OffsetAttribute.class);
        CharTermAttribute char_term_attribute = token_stream.addAttribute(CharTermAttribute.class);

        token_stream.reset();
        
        int start_offset;
        int end_offset = offset_attribute.endOffset();
        
        while(token_stream.incrementToken() && end_offset < 1000) 
        {
            start_offset = offset_attribute.startOffset();
            end_offset = offset_attribute.endOffset();
            
            String term = char_term_attribute.toString();

            if(term.equals("Title:"))
                print_tokens_and_offsets("Title", start_offset, end_offset, token_stream, offset_attribute, char_term_attribute);
            else if(term.equals("Date:"))
                print_tokens_and_offsets("Date", start_offset, end_offset, token_stream, offset_attribute, char_term_attribute);
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        if(args.length != 1) 
            throw new IllegalArgumentException("Usage: java " + BookInfoFinder.class.getName() + " <.txt file path>");
        
        BookInfoFinder finder = new BookInfoFinder();
        
        finder.find_book_info(args[0]);
    } 
}
