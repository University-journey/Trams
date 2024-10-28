// Stworzono za pomocą:
// https://www.youtube.com/watch?v=0wPlzMU-k00&ab_channel=MichaelSambol
// https://www.youtube.com/watch?v=pLIajuc31qk&ab_channel=Insidecode
// ChatGPT: https://chat.openai.com/share/96335c1e-a8d7-4b10-95d6-7fdc960fd730
//
package App.Queue;

import App.Events.Event;

import java.util.Arrays;

public interface HeapPriorityQueue {
    void add(Event event);

    Event poll();

    Event peek();

    boolean isEmpty();
}

